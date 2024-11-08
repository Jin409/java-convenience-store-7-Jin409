package store.model;

public class PromotionProduct extends Product {

    private final Promotion promotion;
    private long promotionQuantity;

    public PromotionProduct(String name, long price, Promotion promotion, long promotionQuantity) {
        super(name, price);
        this.promotion = promotion;
        this.promotionQuantity = promotionQuantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public long getPromotionQuantity() {
        return promotionQuantity;
    }
}
