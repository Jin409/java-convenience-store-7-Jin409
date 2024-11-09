package store.service.product;

import java.time.LocalDate;
import store.model.Product;

public abstract class InventoryHandler {
    protected InventoryHandler next;

    public void setNext(InventoryHandler next) {
        this.next = next;
    }

    public abstract void reduceQuantity(Product product, long orderedQuantity, LocalDate orderedAt);
}
