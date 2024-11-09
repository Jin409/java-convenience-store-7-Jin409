package store.service.product;

import java.time.LocalDate;
import store.model.Product;

public class PromotionItemInventoryHandler extends InventoryHandler {
    @Override
    public void reduceQuantity(Product product, long orderedQuantity, LocalDate orderedAt) {
        if (product.isPromotionAvailable(orderedAt)) {
            orderedQuantity = product.getRemainingAfterApplyingPromotion(orderedQuantity);
        }

        if (next != null) {
            next.reduceQuantity(product, orderedQuantity, orderedAt);
        }
    }
}
