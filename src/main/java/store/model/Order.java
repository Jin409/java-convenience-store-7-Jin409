package store.model;

import java.time.LocalDateTime;

public class Order {
    private final Product product;
    private final long quantity;
    private final LocalDateTime orderedAt;

    public Order(Product product, long quantity, LocalDateTime orderedAt) {
        this.product = product;
        this.quantity = quantity;
        this.orderedAt = orderedAt;
    }
}

