package store.service.product;

import java.time.LocalDate;
import store.model.Product;

public class StockItemInventoryHandler extends InventoryHandler {
    @Override
    public void reduceQuantity(Product product, long orderedQuantity, LocalDate orderedAt) {
        if (orderedQuantity == 0) {
            return;
        }

        if (product.isStockItemUnAvailable()) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }

        product.applyStockReduction(orderedQuantity);

        if (next != null) {
            next.reduceQuantity(product, orderedQuantity, orderedAt);
        }
    }
}