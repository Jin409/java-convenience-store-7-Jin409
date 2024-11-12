package store.service;

import java.util.List;
import store.model.Order;
import store.model.repository.OrderRepository;

public class MembershipDiscountService {
    private final OrderRepository orderRepository;

    public MembershipDiscountService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private static final long MAXIMUM_DISCOUNT_AMOUNT = 8_000;
    private static final long DISCOUNT_UNIT = 1_000;
    private static final double DISCOUNTED_RATE = 0.3;

    public long calculateDiscountedAmount() {
        List<Order> orders = orderRepository.findAll();
        long totalOriginalPrice = orders.stream().filter(Order::isNotPrinted).mapToLong(Order::getOriginalPrice).sum();
        long discountedAmount = (long) (totalOriginalPrice * DISCOUNTED_RATE) / DISCOUNT_UNIT * DISCOUNT_UNIT;
        if (discountedAmount < MAXIMUM_DISCOUNT_AMOUNT) {
            return discountedAmount;
        }
        return MAXIMUM_DISCOUNT_AMOUNT;
    }
}
