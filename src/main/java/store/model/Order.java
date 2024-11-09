package store.model;

import java.time.LocalDate;

public class Order {
    private final Product product;
    private final long quantity;
    private final LocalDate orderedAt;
    private final long discountedPrice;

    public Order(Product product, long quantity, LocalDate orderedAt, long discountedPrice) {
        this.product = product;
        this.quantity = quantity;
        this.orderedAt = orderedAt;
        this.discountedPrice = discountedPrice;
    }
}

