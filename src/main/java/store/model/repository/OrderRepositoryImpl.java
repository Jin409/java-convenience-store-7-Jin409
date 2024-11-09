package store.model.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import store.model.Order;

public class OrderRepositoryImpl implements OrderRepository {
    private static List<Order> orders;

    public OrderRepositoryImpl() {
        orders = new ArrayList<>();
    }

    @Override
    public void save(Order order) {
        orders.add(order);
    }

    @Override
    public List<Order> findAll() {
        return Collections.unmodifiableList(orders);
    }
}