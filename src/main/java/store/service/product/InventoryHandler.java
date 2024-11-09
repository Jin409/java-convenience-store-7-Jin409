package store.service.product;

import java.time.LocalDateTime;
import store.model.Product;

public abstract class InventoryHandler {
    protected InventoryHandler next;

    public void setNext(InventoryHandler next) {
        this.next = next;
    }

    public abstract void reduceQuantity(Product product, long orderedQuantity, LocalDateTime orderedAt);
}
