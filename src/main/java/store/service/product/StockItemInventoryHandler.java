package store.service.product;

import java.time.LocalDateTime;
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
}