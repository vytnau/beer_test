package lt.vn.beer.test.data;

public class RouteServiceData implements Comparable {
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

    @Override
    public int compareTo(Object o) {
        RouteServiceData routeResult = (RouteServiceData) o;
        if(routeResult.distance > this.distance){
            return -1;
        } else if(routeResult.distance > this.distance){
            return 1;
        }
        return 0;
    }
}
