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
            this.promotionQuantity = 0;
            return reduceQuantity - originalQuantity;
        }

        promotionQuantity = promotionQuantity - reduceQuantity;
        return 0;
    }
}