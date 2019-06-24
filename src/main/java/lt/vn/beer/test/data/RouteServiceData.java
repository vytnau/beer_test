package lt.vn.beer.test.data;

public class RouteServiceData {
    private double distance;
    private GeoCodesData point;
    private BeerData beerData;

    public RouteServiceData(double distance, GeoCodesData point, BeerData beerData) {
        this.distance = distance;
        this.point = point;
        this.beerData = beerData;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public GeoCodesData getPoint() {
        return point;
    }

    public void setPoint(GeoCodesData point) {
        this.point = point;
    }

    public BeerData getBeerData() {
        return beerData;
    }

    @Override
    public String toString() {
        return "RouteServiceData{" +
                "distance=" + distance +
                ", point=" + point +
                '}';
    }
}
