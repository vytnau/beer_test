package lt.vn.beer.test.data;

public abstract class BaseData {
    private long id;
    private String name;

    public BaseData(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
