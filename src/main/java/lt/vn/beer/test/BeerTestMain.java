package lt.vn.beer.test;

import lt.vn.beer.test.data.*;
import lt.vn.beer.test.service.DataProvider;
import lt.vn.beer.test.service.FileDataSource;
import lt.vn.beer.test.service.RouteService;

import java.util.List;
import java.util.Map;

public class BeerTestMain {
//    private static double lon = 11.100790;
//    private static double lat = 51.355468;
    private static double lon = 19.43295600;
    private static double lat = 51.74250300;


    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        DataProvider dataProvider = new FileDataSource();
        List<GeoCodes> geoCodes = dataProvider.getBreweryLocations();
        Map<Long, BeerStyles> beerStylesMap = dataProvider.getBeerStyles();
        Map<Long, BeerCategory> beerCategoryMap = dataProvider.getBeerCategories();
        Map<Long, Beer> beer = dataProvider.getBeers();
        Map<Long, Brewery> breweryMap = dataProvider.getBreweries();
//        geoCodes.forEach(g-> System.out.println(g.toString()));
        RouteService routeService = new RouteService(new GeoCodes(0, 0, lat, lon), beer);
        RouteResult result = routeService.findTheRoute(geoCodes);
        double totalDistance = 0;
        System.out.println("Brewery found: " + (result.getRoute().size() - 2));
        for(SearchData r : result.getRoute()) {
            Brewery brewery = breweryMap.get(r.getPoint().getBreweryId());
            if(brewery != null){
                String name = brewery.getName();
                System.out.println("[" + r.getPoint().getBreweryId() + "]" + " "+ name +": " + r.getPoint().getLatitude()
                        + "," + r.getPoint().getLongitude() + " distance " + String.format("%.2f km",r.getDistance()));
            } else {
                System.out.println("[HOME] " + r.getPoint().getLatitude()
                        + "," + r.getPoint().getLongitude() + " distance " + String.format("%.2f km", r.getDistance()));
            }
            // "[id] name, coord, distance"
            totalDistance += r.getDistance();
        };
        System.out.println();
        System.out.println("Distance: " + String.format("%.2f km", totalDistance));
        System.out.println();
        long endTime = System.currentTimeMillis();
        System.out.println("Beer styles: " + result.getBeerStyles().size());
        for(BeerStyleResult r : result.getBeerStyles()) {
            System.out.println(r.getName());

        }

        System.out.println("Program took: " + (endTime - startTime) / 100d + " s");
    }
}
