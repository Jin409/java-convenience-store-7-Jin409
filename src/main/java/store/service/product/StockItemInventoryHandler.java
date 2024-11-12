package store.service.product;

import java.time.LocalDateTime;
import store.model.ModelErrorMessages.OfStockItem;
import store.model.Product;

public class StockItemInventoryHandler extends InventoryHandler {
    @Override
    public void reduceQuantity(Product product, long orderedQuantity, LocalDateTime orderedAt) {
        if (orderedQuantity == 0) {
            return;
        }

        product.applyStockReduction(orderedQuantity);

        if (next != null) {
            next.reduceQuantity(product, orderedQuantity, orderedAt);
        }
    }

    @Override
    public void hasEnoughQuantity(Product product, long orderedQuantity, LocalDateTime orderedAt) {
        if (orderedQuantity <= 0) {
            return;
        }

        if (product.getStockItem().getRemainingAfterReduction(orderedQuantity) > 0) {
            throw new IllegalArgumentException(OfStockItem.NOT_ENOUGH_QUANTITY);
        }
    }
}