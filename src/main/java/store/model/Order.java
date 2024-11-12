package store.model;

public class Order {
    private final Product product;
    private final long quantity;
    private final long discountedPrice;
    private boolean isPrinted;

    public Order(Product product, long quantity, long discountedPrice) {
        this.product = product;
        this.quantity = quantity;
        this.discountedPrice = discountedPrice;
        this.isPrinted = false;
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

    public boolean isNotPrinted() {
        return !isPrinted;
    }

    public void updateToPrinted() {
        isPrinted = true;
    }
}

