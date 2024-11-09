package store.service;

public class PromotionApplyResult {
    private final boolean isInvalid;
    private final String nameOfProduct;
    private final long missingOrderQuantity;

    public PromotionApplyResult(boolean isInvalid, String nameOfProduct, long missingOrderQuantity) {
        this.isInvalid = isInvalid;
        this.nameOfProduct = nameOfProduct;
        this.missingOrderQuantity = missingOrderQuantity;
    }

    public boolean isInvalid() {
        return isInvalid;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }

    public long getMissingOrderQuantity() {
        return missingOrderQuantity;
    }
}