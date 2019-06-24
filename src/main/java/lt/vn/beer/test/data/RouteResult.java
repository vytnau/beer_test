package lt.vn.beer.test.data;

import java.util.List;
import java.util.Set;

public class RouteResult {

    private List<SearchData> route;
    private Set<BeerStyleResult> beerStyles;

    public RouteResult(List<SearchData> route, Set<BeerStyleResult> beerStyles) {
        this.route = route;
        this.beerStyles = beerStyles;
    }

    public List<SearchData> getRoute() {
        return route;
    }

    public void setRoute(List<SearchData> route) {
        this.route = route;
    }

    public Set<BeerStyleResult> getBeerStyles() {
        return beerStyles;
    }

    public void setBeerStyles(Set<BeerStyleResult> beerStyles) {
        this.beerStyles = beerStyles;
    }
}
