package lt.vn.beer.test.data;

public class BeerCategory extends BaseData {
    private String name;

    public BeerCategory(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
