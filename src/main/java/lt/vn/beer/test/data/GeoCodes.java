package lt.vn.beer.test.data;

public class GeoCodes {
    private long id;
    private long breweryId;
    private double latitude;
    private double longitude;

    public GeoCodes(long id, long breweryId, double latitude, double longitude) {
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

    public void setBreweryId(long breweryId) {
        this.breweryId = breweryId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "GeoCodes{" +
                "id=" + id +
                ", breweryId=" + breweryId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
