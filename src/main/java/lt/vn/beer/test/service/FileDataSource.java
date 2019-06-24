package lt.vn.beer.test.service;

import com.opencsv.CSVReader;
import lt.vn.beer.test.data.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileDataSource implements DataProvider {
    private static final String CATEGORIES_FILE = "categories.csv";
    private static final String BEER_STYLES_FILE = "styles.csv";
    private static final String GEO_CODES_FILE = "geocodes.csv";
    private static final String BEER_FILE = "beers.csv";
    private static final String BREWERIES_FILE = "breweries.csv";
    private static final String DATA_SEPARATOR = ",";

    private BufferedReader getFileReader(String fileName){
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(streamReader);
    }

    public List<GeoCodes> getBreweryLocations() {
        List<GeoCodes> breweryLocations = new ArrayList<GeoCodes>();
        BufferedReader reader = getFileReader(GEO_CODES_FILE);
        try {
            boolean first = true;
            for (String line; (line = reader.readLine()) != null; ) {
                if(first){
                    first = false;
                    continue;
                }
                String[] data = line.split(DATA_SEPARATOR);
                long id = Long.parseLong(data[0]);
                long breweryId = Long.parseLong(data[1]);
                double lat = Double.parseDouble(data[2]);;
                double lon = Double.parseDouble(data[3]);;;
                breweryLocations.add(new GeoCodes(id, breweryId, lat, lon));
            }
            reader.close();
        } catch(Exception ex){

        }

        return breweryLocations;
    }

    @Override
    public Map<Long, BeerCategory> getBeerCategories() {
        Map<Long, BeerCategory> beerCategoryMap = new HashMap<>();
        BufferedReader reader = getFileReader(CATEGORIES_FILE);
        try {
            boolean first = true;
            for (String line; (line = reader.readLine()) != null; ) {
                if(first){
                    first = false;
                    continue;
                }
                String[] data = line.split(DATA_SEPARATOR);
                long id = Long.parseLong(data[0]);
                String name = data[1];
                beerCategoryMap.put(id, new BeerCategory(id, name));
            }
            reader.close();
        } catch(Exception ex){

        }
        return beerCategoryMap;
    }

    @Override
    public Map<Long, BeerStyles> getBeerStyles() {
        Map<Long, BeerStyles> beerStyleMap = new HashMap<>();
        BufferedReader reader = getFileReader(BEER_STYLES_FILE);
        try {
            boolean first = true;
            for (String line; (line = reader.readLine()) != null; ) {
                if(first){
                    first = false;
                    continue;
                }
                String[] data = line.split(DATA_SEPARATOR);
                long id = Long.parseLong(data[0]);
                long categoryId = Long.parseLong(data[1]);
                String name = data[2];
                beerStyleMap.put(id, new BeerStyles(id, categoryId, name));
            }
            reader.close();
        } catch(Exception ex){

        }
        return beerStyleMap;
    }

    @Override
    public Map<Long, Beer> getBeers() {
        Map<Long, Beer> beersMap = new HashMap<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(this.getClass().getClassLoader().getResource(BEER_FILE).getFile()))) {
            String[] values = null;
            boolean first = true;
            while ((values = csvReader.readNext()) != null) {
                if(first){
                    first = false;
                    continue;
                }
                try {
                    long id = Long.parseLong(values[0]);
                    long breweryId = Long.parseLong(values[1]);
                    String name = values[2];
                    long categoryId = Long.parseLong(values[3]);
                    long styleId = Long.parseLong(values[4]);
                    Beer beer = beersMap.get(breweryId);
                    if (beer == null) {
                        beersMap.put(breweryId, new Beer(id, breweryId, categoryId, styleId, name));
                    } else {
                        beer.add(styleId, categoryId, name);
                    }
                } catch (NumberFormatException ex){
                    System.err.println("something wrong");
                    //not the data
                }
            }
        }catch (Exception e){
            System.err.println("something wrong");
        }
        return beersMap;
    }

    @Override
    public Map<Long, Brewery> getBreweries() {
        Map<Long, Brewery> breweriesMap = new HashMap<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(this.getClass().getClassLoader().getResource(BREWERIES_FILE).getFile()))) {
            String[] values = null;
            boolean first = true;
            while ((values = csvReader.readNext()) != null) {
                if(first){
                    first = false;
                    continue;
                }
                try {
                    long id = Long.parseLong(values[0]);
                    String name = values[1];
                    breweriesMap.put(id, new Brewery(id, name));
                } catch (NumberFormatException ex){
                    System.err.println("something wrong");
                    //not the data
                }
            }
        }catch (Exception e){
            System.err.println("something wrong");
        }
        return breweriesMap;
    }
}
