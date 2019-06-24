package lt.vn.beer.test.data;

public class BeerStyles extends BaseData{
    private long catId;
    private String name;

    public BeerStyles(long id, long catId, String name) {
        super(id);
        this.catId = catId;
        this.name = name;
    }

    public long getCatId() {
        return catId;
    }

    public void setCatId(long catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BeerStyles{" +
                "catId=" + catId +
                ", name='" + name + '\'' +
                '}';
    }
}
