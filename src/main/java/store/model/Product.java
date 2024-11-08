package store.model;

public class Product {
    private final String name;
    private final long price;
    private long quantity;
    private final Promotion promotion;

    public Product(String name, long price, long quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void reduceQuantity(long soldQuantity) {
        if (quantity < soldQuantity) {
            throw new IllegalArgumentException(ErrorMessages.Product.NOT_ENOUGH_QUANTITY);
        }
        quantity = quantity - soldQuantity;
    }
}

