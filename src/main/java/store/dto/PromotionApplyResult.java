package store.dto;

public class PromotionApplyResult {
    private final String nameOfProduct;
    private final long quantityWithoutPromotion;
    private final long missingOrderQuantity;

    public PromotionApplyResult(String nameOfProduct, long quantityWithoutPromotion, long missingOrderQuantity) {
        this.nameOfProduct = nameOfProduct;
        this.quantityWithoutPromotion = quantityWithoutPromotion;
        this.missingOrderQuantity = missingOrderQuantity;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public long getMissingOrderQuantity() {
        return missingOrderQuantity;
    }

    public boolean hasMissingOrderQuantity() {
        return missingOrderQuantity > 0;
    }

    public boolean hasQuantityWithoutPromotion() {
        return quantityWithoutPromotion > 0;
    }

    public long getQuantityWithoutPromotion() {
        return quantityWithoutPromotion;
    }
}