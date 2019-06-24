package lt.vn.beer.test.data;

public class GeoCodesData {
    private long id;
    private long breweryId;
    private double latitude;
    private double longitude;

    public GeoCodesData(long id, long breweryId, double latitude, double longitude) {
        this.id = id;
        this.breweryId = breweryId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBreweryId() {
        return breweryId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "GeoCodesData{" +
                "id=" + id +
                ", breweryId=" + breweryId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
