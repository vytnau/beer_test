package lt.vn.beer.test.service;

import lt.vn.beer.test.data.*;

import java.util.List;
import java.util.Map;

public interface DataProvider {
    List<GeoCodes> getBreweryLocations();
    Map<Long, BeerCategory> getBeerCategories();
    Map<Long, BeerStyles> getBeerStyles();
    Map<Long, Beer> getBeers();
    Map<Long, Brewery> getBreweries();
}
