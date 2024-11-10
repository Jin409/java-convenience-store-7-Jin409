package store.config;

import store.service.product.InventoryHandler;
import store.service.product.PromotionItemInventoryHandler;
import store.service.product.StockItemInventoryHandler;

public class InventoryHandlerConfig {
    public InventoryHandler inventoryHandler() {
        PromotionItemInventoryHandler promotionItemQuantityManagingHandler = new PromotionItemInventoryHandler();
        StockItemInventoryHandler stockItemQuantityManagingHandler = new StockItemInventoryHandler();

        promotionItemQuantityManagingHandler.setNext(stockItemQuantityManagingHandler);

        return promotionItemQuantityManagingHandler;
    }
}
