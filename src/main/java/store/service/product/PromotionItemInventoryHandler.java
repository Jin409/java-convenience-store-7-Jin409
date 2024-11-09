package store.service.product;

import java.time.LocalDate;
import store.model.Product;

public class PromotionItemInventoryHandler extends InventoryHandler {
    @Override
    public void reduceQuantity(Product product, long orderedQuantity, LocalDate orderedAt) {
        long remainingOrderedQuantity = orderedQuantity;

        if (product.isPromotionAvailable(orderedAt)) {
            remainingOrderedQuantity = product.getRemainingAfterApplyingPromotion(orderedQuantity);
            product.applyPromotionReduction(orderedQuantity);
        }

        if (next != null) {
            next.reduceQuantity(product, remainingOrderedQuantity, orderedAt);
        }
    }
}
