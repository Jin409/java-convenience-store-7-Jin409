package store.model;

import java.util.Collections;
import java.util.List;

public class Receipt {
    private final List<Order> orders;
    private final long membershipDiscountedAmount;

    public Receipt(List<Order> orders, long membershipDiscountedAmount) {
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
