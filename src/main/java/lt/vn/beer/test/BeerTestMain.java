package lt.vn.beer.test;

import lt.vn.beer.test.data.*;
import lt.vn.beer.test.service.DataProvider;
import lt.vn.beer.test.service.FileDataSource;
import lt.vn.beer.test.service.RouteService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeerTestMain {
//    private static double lon = 11.100790;
//    private static double lat = 51.355468;
    private static double lon = 19.43295600;
    private static double lat = 51.74250300;
    private static Map<Long, BreweryData> breweryValueMap;

    //TODO need to do optimization
    //TODO Write some tests
    //TODO user recursion for search

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        DataProvider dataProvider = new FileDataSource();
        List<GeoCodesData> geoCodes = dataProvider.getBreweryLocations();
//        Not need these maps
//        Map<Long, BeerStyleData> beerStylesMap = dataProvider.getBeerStyles();
//        Map<Long, BeerCategoryData> beerCategoryMap = dataProvider.getBeerCategories();
        Map<Long, BeerData> beer = dataProvider.getBeers();
        breweryValueMap = dataProvider.getBreweries();

        RouteService routeService = new RouteService(new GeoCodesData(0, 0, lat, lon), beer);
        RouteResultData result = routeService.findTheRoute(geoCodes);
        printBeerFactories(result.getRoute());
        printBeerStyles(result.getBeerStyles());
        long endTime = System.currentTimeMillis();

        System.out.println("Program took: " + (endTime - startTime) / 100d + " s");
    }

    private static void printBeerFactories(List<RouteServiceData> routes){
        double totalDistance = 0;
        System.out.println("BreweryData found: " + (routes.size() - 2));
        for(RouteServiceData route : routes) {
            BreweryData brewery = breweryValueMap.get(route.getPoint().getBreweryId());
            if(brewery != null){
                String name = brewery.getName();
                System.out.println("[" + route.getPoint().getBreweryId() + "]" + " "+ name +": " + route.getPoint().getLatitude()
                        + "," + route.getPoint().getLongitude() + " distance " + formatDistance(route.getDistance()));
            } else {
                System.out.println("[HOME] " + route.getPoint().getLatitude()
                        + "," + route.getPoint().getLongitude() + " distance " + formatDistance(route.getDistance()));
            }
            totalDistance += route.getDistance();
        };
        System.out.println();
        System.out.println("Distance: " + formatDistance(totalDistance));
        System.out.println();
    }

    private static void printBeerStyles(Set<BeerStyleData> beerStyleResult){
        System.out.println("BeerData styles: " + beerStyleResult.size());
        for(BeerStyleData beerStyle : beerStyleResult) {
            System.out.println(beerStyle.getName());

        }
    }

    private static String formatDistance(double distance){
        return String.format("%.2f km", distance);
    }
}
