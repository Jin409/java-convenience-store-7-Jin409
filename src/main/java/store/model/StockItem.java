package store.model;

import static store.model.ErrorMessages.StockItem.NOT_ENOUGH_QUANTITY;

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
            throw new IllegalArgumentException(NOT_ENOUGH_QUANTITY);
        }
        quantity = quantity - reduceQuantity;
    }
}
