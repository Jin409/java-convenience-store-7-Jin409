package store.model.promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public boolean isApplicable(LocalDateTime orderedAt) {
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