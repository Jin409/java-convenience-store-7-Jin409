package store.model;

import java.util.List;

public class Orders {
    private final List<Order> orders;
    private final double membershipDiscountedAmount;

    public Orders(List<Order> orders, double membershipDiscountedAmount) {
        this.orders = orders;
        this.membershipDiscountedAmount = membershipDiscountedAmount;
    }
}
