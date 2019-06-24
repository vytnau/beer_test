package lt.vn.beer.test.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Beer extends BaseData {
    private long breweryId;
    private String name;
    private List<Long> categoryId;
    private List<Long> styleId;
    private Set<BeerStyleResult> beerStyles;

    public Beer(long id, long breweryId, long categoryId, long styleId, String name) {
        super(id);
        this.breweryId = breweryId;
        this.name = name;
        this.categoryId = new ArrayList<>();
        this.categoryId.add(categoryId);
        this.styleId = new ArrayList<>();
        this.styleId.add(styleId);
        this.beerStyles = new HashSet<>();
        beerStyles.add(new BeerStyleResult(styleId, categoryId, name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(long breweryId) {
        this.breweryId = breweryId;
    }

    public List<Long> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(List<Long> categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getStyleId() {
        return styleId;
    }

    public void setStyleId(List<Long> styleId) {
        this.styleId = styleId;
    }

    public void add(long styleId, long categoryId, String name) {
        this.styleId.add(styleId);
        this.categoryId.add(categoryId);
        this.name = name;
        beerStyles.add(new BeerStyleResult(styleId, categoryId, name));
    }

    public Set<BeerStyleResult> getBeerStyles() {
        return beerStyles;
    }

    public void setBeerStyles(Set<BeerStyleResult> beerStyles) {
        this.beerStyles = beerStyles;
    }
}
