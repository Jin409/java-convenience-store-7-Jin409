package store.model.promotion;

import java.time.LocalDate;

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

    public boolean isApplicable(LocalDate orderedAt) {
        return promotion.isApplicable(orderedAt);
    }

    public long getRemainingAfterReduction(long reduceQuantity) {
        long originalQuantity = promotionQuantity;

        if (reduceQuantity > promotionQuantity) {
            return reduceQuantity - originalQuantity;
        }
        return 0;
    }

    public boolean hasEnoughItems(long reduceQuantity) {
        return promotionQuantity >= reduceQuantity;
    }

    public void reduceQuantity(long reduceQuantity) {
        promotionQuantity = promotionQuantity - reduceQuantity;
    }
}