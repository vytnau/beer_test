package lt.vn.beer.test.data;

public class BeerStyleResult {
    private long beerStyleId;
    private long categoryId;
    private String name;

    public BeerStyleResult(long beerStyleId, long categoryId, String name) {
        this.beerStyleId = beerStyleId;
        this.categoryId = categoryId;
        this.name = name;
    }

    public long getBeerStyleId() {
        return beerStyleId;
    }

    public void setBeerStyleId(long beerStyleId) {
        this.beerStyleId = beerStyleId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof BeerStyleResult){
            BeerStyleResult ptr = (BeerStyleResult) v;
            retVal = ptr.beerStyleId == this.beerStyleId && ptr.categoryId == this.categoryId &&
                    this.name.equals(ptr.name);
        }

        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int)this.categoryId + (int)this.beerStyleId + name.hashCode();
        return hash;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BeerStyleResult{" +
                "beerStyleId=" + beerStyleId +
                ", categoryId=" + categoryId +
                ", name=" + name +
                '}';
    }
}
