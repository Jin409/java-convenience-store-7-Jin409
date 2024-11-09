package store.model;

import java.util.Collections;
import java.util.List;

public class Orders {
    private final List<Order> orders;
    private final long membershipDiscountedAmount;

    public Orders(List<Order> orders, long membershipDiscountedAmount) {
        this.orders = orders;
        this.membershipDiscountedAmount = membershipDiscountedAmount;
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public long getMembershipDiscountedAmount() {
        return membershipDiscountedAmount;
    }
}
