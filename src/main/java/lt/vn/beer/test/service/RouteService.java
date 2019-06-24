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

    private List<RouteServiceData> filterGeoCodesPoints(List<GeoCodesData> geoCodes){
        List<RouteServiceData> filteredGeoCodes = new ArrayList<>();
        for( GeoCodesData geoCode : geoCodes){
            //Check if missing some data about some breweries
            BeerData beerData = beersMap.get(geoCode.getBreweryId());
            if(beerData != null){
                //ignore to far points
                double dist = Haversine.distance(home.getLatitude(), home.getLongitude(), geoCode.getLatitude(), geoCode.getLongitude());
                if (dist * 2 < MAX_DISTANCE) {
                    filteredGeoCodes.add(new RouteServiceData(dist, geoCode, beerData));
                }
            }
        }
        return filteredGeoCodes;
    }

    public RouteResultData findTheRoute(List<GeoCodesData> geoCodes){
        List<RouteServiceData> filteredPoints = filterGeoCodesPoints(geoCodes);
        //construct the route
        Set<BeerStyleData> beerStyleResults = new HashSet<>();
        List<RouteServiceData> route = new ArrayList<>();
        GeoCodesData point = home;
        route.add(new RouteServiceData(0,home, null));
        while(distance < MAX_DISTANCE){
            RouteServiceData maxPoint = searchBeerFactoryWithMostBeerStyles(point, filteredPoints, beerStyleResults);
            double dist = Haversine.distance(maxPoint.getPoint().getLatitude(), maxPoint.getPoint().getLongitude(), home.getLatitude(), home.getLongitude());
            if(distance + dist + maxPoint.getDistance() < MAX_DISTANCE){
                route.add(maxPoint);
                BeerData beer = maxPoint.getBeerData();
                beerStyleResults.addAll(beer.getBeerStyles());
                filteredPoints.remove(maxPoint.getPoint());
                point = maxPoint.getPoint();
                distance += maxPoint.getDistance();
            } else {
                distance += dist;
                route.add(new RouteServiceData(dist, home, null));
                break;
            }
        }
        System.out.println(route.size());
        return new RouteResultData(route, beerStyleResults);
    }

    private RouteServiceData searchBeerFactoryWithMostBeerStyles(GeoCodesData point, List<RouteServiceData> geoCodes, Set<BeerStyleData> beerStyleResults){
        double min = MAX_DISTANCE / 2;
        int beerStyles = 0;
        RouteServiceData minPoint = null;

        for(RouteServiceData geoCode : geoCodes) {
            double dist = 0;
            if(point.equals(home)) {
                dist = Haversine.distance(point.getLatitude(), point.getLongitude(), geoCode.getPoint().getLatitude(), geoCode.getPoint().getLongitude());
            } else {
                dist = geoCode.getDistance();
            }
            BeerData beer = beersMap.get(geoCode.getPoint().getBreweryId());
            int newBeerStyles = newBeerStyles(beer.getBeerStyles(), beerStyleResults);

            if(beerStyles < newBeerStyles){
//            if (dist < min) {
                min = dist;
                beerStyles = newBeerStyles;
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
}
