package lt.vn.beer.test.data;

public abstract class BaseData {
    private long id;

    public BaseData(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "id=" + id +
                '}';
    }
}
