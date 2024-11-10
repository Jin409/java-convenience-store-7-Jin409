package store.service;

import java.util.ArrayList;
import java.util.List;
import store.dto.ReceiptDto;
import store.dto.ReceiptDto.FreeItem;
import store.dto.ReceiptDto.OrderedProduct;
import store.dto.ReceiptDto.PaymentSummary;
import store.model.Order;
import store.model.Receipt;
import store.model.repository.OrderRepository;

public class ReceiptService {
    private final OrderRepository orderRepository;

    public ReceiptService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Receipt createReceiptWithoutDiscount() {
        List<Order> orders = orderRepository.findAll();
        return new Receipt(orders, 0);
    }

    public ReceiptDto getReceipt(Receipt receipt) {
        List<ReceiptDto.OrderedProduct> orderedProducts = getOrderedProduct(receipt);
        List<ReceiptDto.FreeItem> freeItems = getFreeItems(receipt);
        PaymentSummary paymentSummary = getPaymentSummary(orderedProducts, receipt);
        return new ReceiptDto(orderedProducts, freeItems, paymentSummary);
    }

    private List<ReceiptDto.OrderedProduct> getOrderedProduct(Receipt receipt) {
        return receipt.getOrders().stream()
                .map(order -> new OrderedProduct(order.getProduct().getName(), order.getQuantity(),
                        order.getProduct().getPrice())).toList();
    }

    private List<ReceiptDto.FreeItem> getFreeItems(Receipt receipt) {
        List<ReceiptDto.FreeItem> freeItems = new ArrayList<>();

        for (Order order : receipt.getOrders()) {
            if (order.getDiscountedPrice() <= 0) {
                continue;
            }
            freeItems.add(new FreeItem(order.getProduct().getName(), order.countFreeItems()));
        }
        return freeItems;
    }

    private ReceiptDto.PaymentSummary getPaymentSummary(List<ReceiptDto.OrderedProduct> orderedProducts,
                                                        Receipt receipt) {
        long totalPrice = orderedProducts.stream().mapToLong(product -> product.price() * product.quantity()).sum();
        long totalDiscountedAmount = receipt.getOrders().stream().mapToLong(Order::getDiscountedPrice).sum();
        long membershipDiscountedAmount = receipt.getMembershipDiscountedAmount();
        return new PaymentSummary(totalPrice, totalDiscountedAmount, membershipDiscountedAmount);
    }
}
