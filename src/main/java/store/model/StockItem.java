package store.model;

public class StockItem {

    private long quantity;

    public StockItem(long quantity) {
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public void reduceQuantity(long reduceQuantity) {
        if (reduceQuantity > quantity) {
            throw new IllegalArgumentException("Reduce quantity exceeds quantity");
        }
        quantity = quantity - reduceQuantity;
    }
}
