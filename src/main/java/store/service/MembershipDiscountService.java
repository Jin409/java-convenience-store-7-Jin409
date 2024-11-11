package store.service;

import java.util.List;
import store.model.Order;
import store.model.repository.OrderRepository;

public class MembershipDiscountService {
    private final OrderRepository orderRepository;

    public MembershipDiscountService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private static final long MAXIMUM_MEMBERSHIP_DISCOUNTED = 8_000;
    private static final double DISCOUNTED_RATE = 0.3;

    public long calculateDiscountedAmount() {
        List<Order> orders = orderRepository.findAll();
        long totalOriginalPrice = orders.stream().mapToLong(Order::getOriginalPrice).sum();
        long discountedAmount = (long) (totalOriginalPrice * DISCOUNTED_RATE) / 1_000 * 1_000;
        if (discountedAmount < MAXIMUM_MEMBERSHIP_DISCOUNTED) {
            return discountedAmount;
        }
        return MAXIMUM_MEMBERSHIP_DISCOUNTED;
    }
}
