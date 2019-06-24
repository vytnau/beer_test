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

    private List<GeoCodesData> filterGeoCodesPoints(List<GeoCodesData> geoCodes){
        List<GeoCodesData> filteredGeoCodes = new ArrayList<>();
        for( GeoCodesData geoCode : geoCodes){
            //Check if missing some data about some breweries
            if(beersMap.get(geoCode.getBreweryId())!= null){
                //ignore to far points
                double dist = Haversine.distance(home.getLatitude(), home.getLongitude(), geoCode.getLatitude(), geoCode.getLongitude());
                if (dist * 2 < MAX_DISTANCE) {
                    filteredGeoCodes.add(geoCode);
                }
            }
        }
        return filteredGeoCodes;
    }

    public RouteResultData findTheRoute(List<GeoCodesData> geoCodes){
        List<GeoCodesData> filteredPoints = filterGeoCodesPoints(geoCodes);
        //construct the route
        Set<BeerStyleData> beerStyleResults = new HashSet<>();
        List<RouteServiceData> route = new ArrayList<>();
        GeoCodesData point = home;
        route.add(new RouteServiceData(0,home));
        while(distance < MAX_DISTANCE){
            //Reik visad perksaiciuoti MaxDistance ir tureti nauja reiksme
            RouteServiceData minPont = searchNearestPoint(point, filteredPoints, beerStyleResults);
            double dist = Haversine.distance(minPont.getPoint().getLatitude(), minPont.getPoint().getLongitude(), home.getLatitude(), home.getLongitude());
            if(distance + dist + minPont.getDistance() < MAX_DISTANCE){
                route.add(minPont);
                BeerData beer = beersMap.get(minPont.getPoint().getBreweryId());
                beerStyleResults.addAll(beer.getBeerStyles());
                filteredPoints.remove(minPont.getPoint());
                point = minPont.getPoint();
                distance += minPont.getDistance();
            } else {
                distance += dist;
                route.add(new RouteServiceData(dist, home));
                break;
            }
        }
        System.out.println(route.size());
        return new RouteResultData(route, beerStyleResults);
    }

    private RouteServiceData searchNearestPoint(GeoCodesData point, List<GeoCodesData> geoCodes, Set<BeerStyleData> beerStyleResults){
        double min = MAX_DISTANCE / 2;
        int beerStyles = 0;
        GeoCodesData minPoint = null;
        for(GeoCodesData g : geoCodes) {
            //TODO need to change criteria
            double dist = Haversine.distance(point.getLatitude(), point.getLongitude(), g.getLatitude(), g.getLongitude());
            BeerData beer = beersMap.get(g.getBreweryId());
            int newBeerStyles = newBeerStyles(beer.getBeerStyles(), beerStyleResults);

            if(beerStyles < newBeerStyles){
//            if (dist < min) {
                min = dist;
                beerStyles = newBeerStyles;
                minPoint = g;
            }
        };
        return new RouteServiceData(min, minPoint);
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
