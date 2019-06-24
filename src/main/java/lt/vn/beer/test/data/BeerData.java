package lt.vn.beer.test.data;

import java.util.HashSet;
import java.util.Set;

public class BeerData extends BaseData {
    private long breweryId;
    private Set<BeerStyleData> beerStyles;

    public BeerData(long id, long breweryId, long categoryId, long styleId, String name) {
        super(id, name);
        this.breweryId = breweryId;
        this.beerStyles = new HashSet<>();
        beerStyles.add(new BeerStyleData(styleId, categoryId, name));
    }

    public long getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(long breweryId) {
        this.breweryId = breweryId;
    }

    public void add(long styleId, long categoryId, String name) {
        beerStyles.add(new BeerStyleData(styleId, categoryId, name));
    }

    public Set<BeerStyleData> getBeerStyles() {
        return beerStyles;
    }

    public void setBeerStyles(Set<BeerStyleData> beerStyles) {
        this.beerStyles = beerStyles;
    }
}
