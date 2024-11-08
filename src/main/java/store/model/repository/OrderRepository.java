package store.model.repository;

import java.util.List;
import store.model.Order;

public interface OrderRepository {
    void save(Order order);

    List<Order> findAll();
}
