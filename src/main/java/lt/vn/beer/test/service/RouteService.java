package lt.vn.beer.test.service;

import lt.vn.beer.test.data.*;
import lt.vn.beer.test.util.Haversine;

import java.util.*;

public class RouteService {
    private GeoCodesData home;
    private static final long MAX_DISTANCE = 2000;
    private long distance;
    private Map<Long, BeerData> beersMap;

    public RouteService(GeoCodesData home, Map<Long, BeerData> beersMap){
        this.home = home;
        distance = 0;
        this.beersMap = beersMap;
    }

    private List<RouteServiceData> filterGeoCodesPoints(List<?> geoCodes, GeoCodesData home){
        List<RouteServiceData> filteredGeoCodes = new ArrayList<>();
        for(Object geoCode : geoCodes){
            //Check if missing some data about some breweries
            long breweryId = 0;
            GeoCodesData geoData = null;
            if(geoCode instanceof GeoCodesData){
                breweryId = ((GeoCodesData)geoCode).getBreweryId();
                geoData = ((GeoCodesData)geoCode);
            } else {
                breweryId = ((RouteServiceData)geoCode).getPoint().getBreweryId();
                geoData = ((RouteServiceData)geoCode).getPoint();
            }
            BeerData beerData = beersMap.get(breweryId);
            if(beerData != null){
                //ignore to far points
                double dist = Haversine.distance(home.getLatitude(), home.getLongitude(), geoData.getLatitude(), geoData.getLongitude());
                if (dist * 2 < MAX_DISTANCE) {
                    if(geoCode instanceof GeoCodesData) {
                        filteredGeoCodes.add(new RouteServiceData(dist, geoData, beerData));
                    } else {
                        ((RouteServiceData)geoCode).setDistance(dist);
                        filteredGeoCodes.add((RouteServiceData)geoCode);
                    }
                }
            }
        }
        return filteredGeoCodes;
    }

    private List<RouteServiceData> filterPointsSubset(List<RouteServiceData> geoCodes, GeoCodesData home){
        List<RouteServiceData> filteredGeoCodes = new ArrayList<>();
        for( RouteServiceData geoCode : geoCodes){
            //Check if missing some data about some breweries
            BeerData beerData = beersMap.get(geoCode.getPoint().getBreweryId());
            if(beerData != null){
                //ignore to far points
                double dist = Haversine.distance(home.getLatitude(), home.getLongitude(), geoCode.getPoint().getLatitude(), geoCode.getPoint().getLongitude());
                if (dist * 2 < MAX_DISTANCE) {
                    geoCode.setDistance(dist);
                    filteredGeoCodes.add(geoCode);
                }
            }
        }
        return filteredGeoCodes;
    }

    public RouteResultData findTheRoute(List<GeoCodesData> geoCodes){
        List<RouteServiceData> filteredPoints = filterGeoCodesPoints(geoCodes, home);
        //construct the route
        Collections.sort(filteredPoints);
        Set<BeerStyleData> beerStyleResults = new HashSet<>();
        List<RouteServiceData> route = new ArrayList<>();
        GeoCodesData point = home;
        route.add(new RouteServiceData(0,home, null));
        System.out.println(filteredPoints.size());
        int index = 0;
        while(distance < MAX_DISTANCE){
            RouteServiceData maxPoint = searchBeerFactoryWithMostBeerStyles(point, filteredPoints, beerStyleResults);
            double dist = Haversine.distance(maxPoint.getPoint().getLatitude(), maxPoint.getPoint().getLongitude(),
                    home.getLatitude(), home.getLongitude());
            if(distance + dist + maxPoint.getDistance() < MAX_DISTANCE){
                route.add(maxPoint);
                BeerData beer = maxPoint.getBeerData();
                beerStyleResults.addAll(beer.getBeerStyles());
                filteredPoints.remove(maxPoint.getPoint());
                point = maxPoint.getPoint();
                distance += maxPoint.getDistance();
                filteredPoints = filterBeerFactory(filteredPoints, beerStyleResults);
                filteredPoints = filterGeoCodesPoints(filteredPoints, point);
                Collections.sort(filteredPoints);
            } else {
                findLastPoint(route.get(route.size()-1), route, beerStyleResults);
                distance += route.get(route.size()-1).getDistance();
                break;
            }
        }
        System.out.println(route.size());
        return new RouteResultData(route, beerStyleResults);
    }

    private void findLastPoint(RouteServiceData point, List<RouteServiceData> routes, Set<BeerStyleData> beerStyleResults){
        double dist = Haversine.distance(point.getPoint().getLatitude(), point.getPoint().getLongitude(),
                home.getLatitude(), home.getLongitude());
        if(distance + dist > MAX_DISTANCE){
            RouteServiceData route = routes.get(routes.size()-1);
            beerStyleResults.removeAll(route.getBeerData().getBeerStyles());
            routes.remove(route);
            findLastPoint(routes.get(routes.size()-1), routes, beerStyleResults);
        } else {
            routes.add(new RouteServiceData(dist, home, null));
        }
    }

    private RouteServiceData searchBeerFactoryWithMostBeerStyles(GeoCodesData point, List<RouteServiceData> geoCodes, Set<BeerStyleData> beerStyleResults){
        double min = MAX_DISTANCE / 2;
        double maxVisitPoint = 1;
        RouteServiceData minPoint = null;
        int i = 0;
        for(RouteServiceData geoCode : geoCodes) {
            i++;
            double dist = 0;
            if(point.equals(home)) {
                dist = Haversine.distance(point.getLatitude(), point.getLongitude(), geoCode.getPoint().getLatitude(), geoCode.getPoint().getLongitude());
            } else {
                dist = geoCode.getDistance();
            }
            BeerData beer = beersMap.get(geoCode.getPoint().getBreweryId());
            int newBeerStyles = newBeerStyles(beer.getBeerStyles(), beerStyleResults);
            double visitPoint = (1 - newBeerStyles / 100) + (1 - dist / MAX_DISTANCE);
            if(maxVisitPoint < visitPoint){
              maxVisitPoint = visitPoint;
              min = dist;
              minPoint = geoCode;
            }
        };
        return new RouteServiceData(min, minPoint.getPoint(), minPoint.getBeerData());
    }

    private int newBeerStyles(Set<BeerStyleData> beerStyles, Set<BeerStyleData> beerStyleResults){
        int newStyles = 0;
        for( BeerStyleData b : beerStyles) {
            if(!beerStyleResults.contains(b)){
                newStyles++;
            }
        };
        return newStyles;
    }

    private List<RouteServiceData> filterBeerFactory(List<RouteServiceData> geoCodes, Set<BeerStyleData> beerStyleResults){
        List<RouteServiceData> filteredData = new ArrayList<>();
        geoCodes.forEach( g -> {
            if(newBeerStyles(g.getBeerData().getBeerStyles(), beerStyleResults) > 0){
                filteredData.add(g);
            }
        });
        return filteredData;
    }
}
