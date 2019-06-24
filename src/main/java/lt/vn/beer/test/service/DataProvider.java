package lt.vn.beer.test.service;

import lt.vn.beer.test.data.*;

import java.util.List;
import java.util.Map;

public interface DataProvider {
    List<GeoCodesData> getBreweryLocations();
    Map<Long, BeerCategoryData> getBeerCategories();
    Map<Long, BeerStyleData> getBeerStyles();
    Map<Long, BeerData> getBeers();
    Map<Long, BreweryData> getBreweries();
}
