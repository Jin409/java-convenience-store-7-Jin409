package store.service;


import static store.service.ErrorMessages.OrderService.INVALID_PRODUCT;

import java.time.LocalDateTime;
import java.util.List;
import store.dto.OrderRegisterDto;
import store.model.Order;
import store.model.Product;
import store.model.repository.OrderRepository;
import store.model.repository.ProductRepository;

public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void saveOrders(List<OrderRegisterDto> orderRegisterDtos) {
        for (OrderRegisterDto orderRegisterDto : orderRegisterDtos) {
            Product product = productRepository.findByName(orderRegisterDto.name())
                    .orElseThrow(() -> new IllegalArgumentException(INVALID_PRODUCT));

            Order order = new Order(product, orderRegisterDto.quantity(), LocalDateTime.now());
            orderRepository.save(order);
        }
    }
}
