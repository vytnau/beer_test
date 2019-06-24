package lt.vn.beer.test.data;

public class RouteServiceData {
    private double distance;
    private GeoCodesData point;

    public RouteServiceData(double distance, GeoCodesData point) {
        this.distance = distance;
        this.point = point;
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

    @Override
    public String toString() {
        return "RouteServiceData{" +
                "distance=" + distance +
                ", point=" + point +
                '}';
    }
}
