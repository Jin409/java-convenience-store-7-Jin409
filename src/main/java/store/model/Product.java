package store.model;

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
}

