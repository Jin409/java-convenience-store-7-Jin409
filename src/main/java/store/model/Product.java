package store.model;

import java.time.LocalDate;
import store.model.promotion.PromotionItem;

public class Product {
    private final String name;
    private final long price;
    private final StockItem stockItem;
    private final PromotionItem promotionItem;

    public Product(String name, long price, StockItem stockItem, PromotionItem promotionItem) {
        this.name = name;
        this.price = price;
        this.stockItem = stockItem;
        this.promotionItem = promotionItem;
    }

    public String getName() {
        return name;
    }

    public boolean hasPromotion() {
        return promotionItem != null;
    }

    public boolean hasStock() {
        return stockItem != null;
    }

    public long getPrice() {
        return price;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public PromotionItem getPromotionItem() {
        return promotionItem;
    }

    public boolean isPromotionAvailable(LocalDate orderedAt) {
        if (promotionItem == null) {
            return false;
        }
        return promotionItem.isApplicable(orderedAt);
    }

    public long getRemainingAfterApplyingPromotion(long reduceQuantity) {
        return promotionItem.getRemainingAfterReduction(reduceQuantity);
    }

    public void applyPromotionReduction(long reduceQuantity) {
        promotionItem.reduceQuantity(reduceQuantity);
    }

    public void applyStockReduction(long reduceQuantity) {
        stockItem.reduceQuantity(reduceQuantity);
    }

    public boolean hasEnoughPromotionItems(long orderedQuantity) {
        return promotionItem.hasEnoughItems(orderedQuantity);
    }
}

