package lt.vn.beer.test.service;

import lt.vn.beer.test.data.*;
import lt.vn.beer.test.util.Haversine;

import java.util.*;

public class RouteService {
    private GeoCodesData home;
    private List<RouteServiceData> route;
    private static final long MAX_DISTANCE = 2000;
    private long distance;
    private List<GeoCodesData> filteredPoints;
    private Map<Long, BeerData> beersMap;
    private Set<BeerStyleData> beerStyleResults;

    public RouteService(GeoCodesData home, Map<Long, BeerData> beersMap){
        this.home = home;
        route = new ArrayList<>();
        filteredPoints = new ArrayList<>();
        route.add(new RouteServiceData(0,home));
        distance = 0;
        this.beersMap = beersMap;
        beerStyleResults = new HashSet<>();
    }

    public RouteResultData findTheRoute(List<GeoCodesData> geoCodes){
        //filter out too far breweries.
        geoCodes.forEach(g -> {
            if(beersMap.get(g.getBreweryId())== null){
                System.err.println("something wrong: " + g);

            } else {
//                if(beersMap.get(g.getBreweryId()).getStyleId().size() > 2) {
                double dist = Haversine.distance(home.getLatitude(), home.getLongitude(), g.getLatitude(), g.getLongitude());
                if (dist * 2 < MAX_DISTANCE) {
                    filteredPoints.add(g);
                }
            }
        });
        System.out.println(filteredPoints.size());
        //construct the route
        GeoCodesData point = home;
        while(distance < MAX_DISTANCE){
            //Reik visad perksaiciuoti MaxDistance ir tureti nauja reiksme
            RouteServiceData minPont = searchNearestPoint(point, filteredPoints);
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

    private RouteServiceData searchNearestPoint(GeoCodesData point, List<GeoCodesData> geoCodes){
        double min = MAX_DISTANCE / 2;
        int beerStyles = 0;
        GeoCodesData minPoint = null;
        for(GeoCodesData g : geoCodes) {
            //TODO need to change criteria
            double dist = Haversine.distance(point.getLatitude(), point.getLongitude(), g.getLatitude(), g.getLongitude());
            BeerData beer = beersMap.get(g.getBreweryId());
            int newBeerStyles = newBeerStyles(beer.getBeerStyles());

            if(beerStyles < newBeerStyles){
//            if (dist < min) {
                min = dist;
                beerStyles = newBeerStyles;
                minPoint = g;
            }
        };
        return new RouteServiceData(min, minPoint);
    }

    private int newBeerStyles(Set<BeerStyleData> beerStyles){
        int newStyles = 0;
        for( BeerStyleData b : beerStyles) {
            if(!beerStyleResults.contains(b)){
                newStyles++;
            }
        };
        return newStyles;
    }
}
