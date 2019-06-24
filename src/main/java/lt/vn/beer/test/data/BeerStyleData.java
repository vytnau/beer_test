package lt.vn.beer.test.data;

public class BeerStyleData extends BaseData{
    private long catId;

    public BeerStyleData(long id, long catId, String name) {
        super(id, name);
        this.catId = catId;
    }

    public long getCatId() {
        return catId;
    }

    public boolean equals(Object v) {
        boolean retVal = false;

        if (v instanceof BeerStyleData){
            BeerStyleData ptr = (BeerStyleData) v;
            retVal = ptr.getCatId() == this.getCatId() && ptr.getId() == this.getId() &&
                    this.getName().equals(ptr.getName());
        }

        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int)this.getCatId() + (int)this.getId() + this.getName().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return "BeerStyleData{" +
                "catId=" + catId +
                '}';
    }
}
