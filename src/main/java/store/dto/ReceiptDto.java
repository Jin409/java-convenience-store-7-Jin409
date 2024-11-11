package store.dto;

import java.util.List;

public class ReceiptDto {
    private final List<OrderedProduct> orderedProducts;
    private final List<FreeItem> freeItems;
    private final PaymentSummary paymentSummary;

    public ReceiptDto(List<OrderedProduct> orderedProducts, List<FreeItem> freeItems, PaymentSummary paymentSummary) {
        this.orderedProducts = orderedProducts;
        this.freeItems = freeItems;
        this.paymentSummary = paymentSummary;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public PaymentSummary getPaymentSummary() {
        return paymentSummary;
    }

    public List<FreeItem> getFreeItems() {
        return freeItems;
    }

    public long countTotalOrderedQuantity() {
        return orderedProducts.stream()
                .mapToLong(OrderedProduct::quantity)
                .sum();
    }

    public record OrderedProduct(String nameOfProduct, long quantity, long price) {
    }

    public record FreeItem(String nameOfProduct, long quantity) {
    }

    public record PaymentSummary(long totalPrice, long promotionDiscountedPrice, long membershipDiscountedPrice) {
    }
}
