package lt.vn.beer.test.data;

import java.util.List;
import java.util.Set;

public class RouteResultData {

    private List<RouteServiceData> route;
    private Set<BeerStyleData> beerStyles;

    public RouteResultData(List<RouteServiceData> route, Set<BeerStyleData> beerStyles) {
        this.route = route;
        this.beerStyles = beerStyles;
    }

    public List<RouteServiceData> getRoute() {
        return route;
    }

    public void setRoute(List<RouteServiceData> route) {
        this.route = route;
    }

    public Set<BeerStyleData> getBeerStyles() {
        return beerStyles;
    }

    public void setBeerStyles(Set<BeerStyleData> beerStyles) {
        this.beerStyles = beerStyles;
    }
}
