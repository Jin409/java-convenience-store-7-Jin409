package store.model;

public class Order {
    private final Product product;
    private final long quantity;
    private final long discountedPrice;

    public Order(Product product, long quantity, long discountedPrice) {
        this.product = product;
        this.quantity = quantity;
        this.discountedPrice = discountedPrice;
    }

    public long getOriginalPrice() {
        return product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public long getDiscountedPrice() {
        return discountedPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public long countFreeItems() {
        return discountedPrice / product.getPrice();
    }
}

