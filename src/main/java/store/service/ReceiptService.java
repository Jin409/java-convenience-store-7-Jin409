package store.service;

import java.util.ArrayList;
import java.util.List;
import store.dto.ReceiptDto;
import store.dto.ReceiptDto.FreeItem;
import store.dto.ReceiptDto.OrderedProduct;
import store.dto.ReceiptDto.PaymentSummary;
import store.model.Order;
import store.model.Orders;

public class ReceiptService {
    public ReceiptDto getReceipt(Orders orders) {
        List<ReceiptDto.OrderedProduct> orderedProducts = getOrderedProduct(orders);
        List<ReceiptDto.FreeItem> freeItems = getFreeItems(orders);
        PaymentSummary paymentSummary = getPaymentSummary(orderedProducts, orders);
        return new ReceiptDto(orderedProducts, freeItems, paymentSummary);
    }

    private List<ReceiptDto.OrderedProduct> getOrderedProduct(Orders orders) {
        return orders.getOrders().stream()
                .map(order -> new OrderedProduct(order.getProduct().getName(), order.getQuantity(),
                        order.getProduct().getPrice())).toList();
    }

    private List<ReceiptDto.FreeItem> getFreeItems(Orders orders) {
        List<ReceiptDto.FreeItem> freeItems = new ArrayList<>();

        for (Order order : orders.getOrders()) {
            if (order.getDiscountedPrice() <= 0) {
                continue;
            }
            freeItems.add(new FreeItem(order.getProduct().getName(), order.countFreeItems()));
        }
        return freeItems;
    }

    private ReceiptDto.PaymentSummary getPaymentSummary(List<ReceiptDto.OrderedProduct> orderedProducts,
                                                        Orders orders) {
        long totalPrice = orderedProducts.stream().mapToLong(product -> product.price() * product.quantity()).sum();
        long totalDiscountedAmount = orders.getOrders().stream().mapToLong(Order::getDiscountedPrice).sum();
        long membershipDiscountedAmount = orders.getMembershipDiscountedAmount();
        return new PaymentSummary(totalPrice, totalDiscountedAmount, membershipDiscountedAmount);
    }
}
