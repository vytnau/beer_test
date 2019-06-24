package lt.vn.beer.test.data;

public class SearchData {
    private double distance;
    private GeoCodes point;

    public SearchData(double distance, GeoCodes point) {
        this.distance = distance;
        this.point = point;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public GeoCodes getPoint() {
        return point;
    }

    public void setPoint(GeoCodes point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "distance=" + distance +
                ", point=" + point +
                '}';
    }
}
