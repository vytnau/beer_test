package lt.vn.beer.test.service;

import lt.vn.beer.test.data.*;
import lt.vn.beer.test.util.Haversine;

import java.util.*;

public class RouteService {
    private GeoCodes home;
    private List<SearchData> route;
    private static final long MAX_DISTANCE = 2000;
    private long distance;
    private List<GeoCodes> filteredPoints;
    private Map<Long, Beer> beersMap;
    private Set<BeerStyleResult> beerStyleResults;

    public RouteService(GeoCodes home, Map<Long, Beer> beersMap){
        this.home = home;
        route = new ArrayList<>();
        filteredPoints = new ArrayList<>();
        route.add(new SearchData(0,home));
        distance = 0;
        this.beersMap = beersMap;
        beerStyleResults = new HashSet<>();
    }

    public RouteResult findTheRoute(List<GeoCodes> geoCodes){
        //filter out too far breweries.
        geoCodes.forEach(g -> {
            if(beersMap.get(g.getBreweryId())== null){
                System.err.println("something wrong: " + g);

            } else if(beersMap.get(g.getBreweryId()).getStyleId().size() > 2) {
                double dist = Haversine.distance(home.getLatitude(), home.getLongitude(), g.getLatitude(), g.getLongitude());
                if (dist * 2 < MAX_DISTANCE) {
                    filteredPoints.add(g);
                }
            }
        });
        System.out.println(filteredPoints.size());
        //construct the route
        GeoCodes point = home;
        while(distance < MAX_DISTANCE){
            //Reik visad perksaiciuoti MaxDistance ir tureti nauja reiksme
            SearchData minPont = searchNearestPoint(point, filteredPoints);
            double dist = Haversine.distance(minPont.getPoint().getLatitude(), minPont.getPoint().getLongitude(), home.getLatitude(), home.getLongitude());
            if(distance + dist + minPont.getDistance() < MAX_DISTANCE){
                route.add(minPont);
                Beer beer = beersMap.get(minPont.getPoint().getBreweryId());
                beerStyleResults.addAll(beer.getBeerStyles());
                filteredPoints.remove(minPont.getPoint());
                point = minPont.getPoint();
                distance += minPont.getDistance();
            } else {
                distance += dist;
                route.add(new SearchData(dist, home));
                break;
            }
        }
        System.out.println(route.size());
        return new RouteResult(route, beerStyleResults);
    }

    private SearchData searchNearestPoint(GeoCodes point, List<GeoCodes> geoCodes){
        double min = MAX_DISTANCE / 2;
        int beerStyles = 0;
        GeoCodes minPoint = null;
        for(GeoCodes g : geoCodes) {
            //TODO need to change criteria
            double dist = Haversine.distance(point.getLatitude(), point.getLongitude(), g.getLatitude(), g.getLongitude());
            Beer beer = beersMap.get(g.getBreweryId());
            int newBeerStyles = newBeerStyles(beer.getBeerStyles());

            if(beerStyles < newBeerStyles){
//            if (dist < min) {
                min = dist;
                beerStyles = newBeerStyles;
                minPoint = g;
            }
        };
        return new SearchData(min, minPoint);
    }

    private int newBeerStyles(Set<BeerStyleResult> beerStyles){
        int newStyles = 0;
        for( BeerStyleResult b : beerStyles) {
            if(!beerStyleResults.contains(b)){
                newStyles++;
            }
        };
        return newStyles;
    }
}
