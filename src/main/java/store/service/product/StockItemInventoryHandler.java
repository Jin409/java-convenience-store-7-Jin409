package store.service.product;

import java.time.LocalDate;
import store.model.Product;

public class StockItemInventoryHandler extends InventoryHandler {
    @Override
    public void reduceQuantity(Product product, long orderedQuantity, LocalDate orderedAt) {
        if (orderedQuantity == 0) {
            return;
        }

        product.applyStockReduction(orderedQuantity);

        if (next != null) {
            next.reduceQuantity(product, orderedQuantity, orderedAt);
        }
    }
}