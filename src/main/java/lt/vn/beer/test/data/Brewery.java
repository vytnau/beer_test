package lt.vn.beer.test.data;

public class Brewery extends BaseData {
    private String name;

    public Brewery(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
