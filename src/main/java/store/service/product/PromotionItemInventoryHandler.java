package store.service.product;

import java.time.LocalDateTime;
import store.model.Product;

public class PromotionItemInventoryHandler extends InventoryHandler {
    @Override
    public void reduceQuantity(Product product, long orderedQuantity, LocalDateTime orderedAt) {
        long remainingOrderedQuantity = orderedQuantity;

        if (product.isPromotionAvailable(orderedAt)) {
            remainingOrderedQuantity = product.getRemainingAfterApplyingPromotion(orderedQuantity);
            product.applyPromotionReduction(orderedQuantity);
        }

        if (next != null) {
            next.reduceQuantity(product, remainingOrderedQuantity, orderedAt);
        }
    }

    @Override
    public void hasEnoughQuantity(Product product, long orderedQuantity, LocalDateTime orderedAt) {
        long remainingOrderedQuantity = orderedQuantity;

        if (product.isPromotionAvailable(orderedAt)) {
            remainingOrderedQuantity = product.getRemainingAfterApplyingPromotion(orderedQuantity);
        }

        if (remainingOrderedQuantity <= 0) {
            return;
        }

        if (next != null) {
            next.hasEnoughQuantity(product, remainingOrderedQuantity, orderedAt);
        }
    }
}
