package lt.vn.beer.test.service;

import com.opencsv.CSVReader;
import lt.vn.beer.test.data.*;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDataSource implements DataProvider {
    private static final String CATEGORIES_FILE = "categories.csv";
    private static final String BEER_STYLES_FILE = "styles.csv";
    private static final String GEO_CODES_FILE = "geocodes.csv";
    private static final String BEER_FILE = "beers.csv";
    private static final String BREWERIES_FILE = "breweries.csv";

    public List<GeoCodesData> getBreweryLocations() {
        List<GeoCodesData> breweryLocations = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(getFilePath(GEO_CODES_FILE)))) {
            boolean first = true;
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                long id = Long.parseLong(values[0]);
                long breweryId = Long.parseLong(values[1]);
                double lat = Double.parseDouble(values[2]);
                double lon = Double.parseDouble(values[3]);
                breweryLocations.add(new GeoCodesData(id, breweryId, lat, lon));
            }
        } catch (Exception e) {
            printErrorMessage(GEO_CODES_FILE, e);
        }

        return breweryLocations;
    }

    @Override
    public Map<Long, BeerCategoryData> getBeerCategories() {
        Map<Long, BeerCategoryData> beerCategoryMap = new HashMap<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(getFilePath(CATEGORIES_FILE)))) {
            boolean first = true;
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                long id = Long.parseLong(values[0]);
                String name = values[1];
                beerCategoryMap.put(id, new BeerCategoryData(id, name));
            }
        } catch (Exception e) {
            printErrorMessage(CATEGORIES_FILE, e);
        }
        return beerCategoryMap;
    }

    @Override
    public Map<Long, BeerStyleData> getBeerStyles() {
        Map<Long, BeerStyleData> beerStyleMap = new HashMap<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(getFilePath(BEER_STYLES_FILE)))) {
            String[] values = null;
            boolean first = true;
            while ((values = csvReader.readNext()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                try {

                } catch (Exception e) {

                }
                long id = Long.parseLong(values[0]);
                long categoryId = Long.parseLong(values[1]);
                String name = values[2];
                beerStyleMap.put(id, new BeerStyleData(id, categoryId, name));
            }
        } catch (Exception e) {
            printErrorMessage(BEER_STYLES_FILE, e);
        }
        return beerStyleMap;
    }

    @Override
    public Map<Long, BeerData> getBeers() {
        Map<Long, BeerData> beersMap = new HashMap<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(getFilePath(BEER_FILE)))) {
            String[] values = null;
            boolean first = true;
            while ((values = csvReader.readNext()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                long id = Long.parseLong(values[0]);
                long breweryId = Long.parseLong(values[1]);
                String name = values[2];
                long categoryId = Long.parseLong(values[3]);
                long styleId = Long.parseLong(values[4]);
                BeerData beer = beersMap.get(breweryId);
                if (beer == null) {
                    beersMap.put(breweryId, new BeerData(id, breweryId, categoryId, styleId, name));
                } else {
                    beer.add(styleId, categoryId, name);
                }
            }
        } catch (Exception e) {
            printErrorMessage(BEER_FILE, e);
        }
        return beersMap;
    }

    @Override
    public Map<Long, BreweryData> getBreweries() {
        Map<Long, BreweryData> breweriesMap = new HashMap<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(getFilePath(BREWERIES_FILE)))) {
            String[] values = null;
            boolean first = true;
            while ((values = csvReader.readNext()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                long id = Long.parseLong(values[0]);
                String name = values[1];
                breweriesMap.put(id, new BreweryData(id, name));
            }
        } catch (Exception e) {
            printErrorMessage(BREWERIES_FILE, e);
        }
        return breweriesMap;
    }

    private String getFilePath(String fileName) {
        return this.getClass().getClassLoader().getResource(fileName).getFile();
    }

    private void printErrorMessage(String file, Exception e) {
        System.err.println("Something went wrong by reading " + file + "." + e);
    }
}
