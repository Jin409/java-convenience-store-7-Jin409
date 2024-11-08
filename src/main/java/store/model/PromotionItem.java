package store.model;

public class PromotionItem {

    private final Promotion promotion;
    private long promotionQuantity;

    public PromotionItem(Promotion promotion, long promotionQuantity) {
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