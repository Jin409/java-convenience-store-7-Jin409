package store.model;

public class DefaultProduct extends Product {

    private long quantity;

    public DefaultProduct(String name, long price, long quantity) {
        super(name, price);
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
