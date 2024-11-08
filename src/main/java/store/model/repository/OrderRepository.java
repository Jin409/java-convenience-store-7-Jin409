package store.model.repository;

import store.model.Order;

public interface OrderRepository {
    void save(Order order);
}
