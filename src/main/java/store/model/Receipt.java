package store.model;

import java.util.Collections;
import java.util.List;

public class Receipt {
    private final List<Order> orders;
    private final long membershipDiscountedAmount;

    public Receipt(List<Order> orders, long membershipDiscountedAmount) {
        this.orders = orders;
        if (orders.stream().mapToLong(Order::getDiscountedPrice).sum() > membershipDiscountedAmount) {
            this.membershipDiscountedAmount = 0;
            return;
        }
        this.membershipDiscountedAmount = membershipDiscountedAmount;
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public long getMembershipDiscountedAmount() {
        return membershipDiscountedAmount;
    }
}
