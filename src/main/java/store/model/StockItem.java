package store.model;

public class StockItem {

    private long quantity;

    public StockItem(long quantity) {
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
