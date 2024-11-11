package store.service;


import static store.service.ErrorMessages.OrderService.INVALID_PRODUCT;

import store.dto.OrderRegisterDto;
import store.model.Order;
import store.model.Product;
import store.model.repository.OrderRepository;
import store.model.repository.ProductRepository;
import store.service.product.InventoryHandler;

public class OrderService {
    private final ProductRepository productRepository;
    private final InventoryHandler inventoryHandler;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, InventoryHandler inventoryHandler,
                        OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.inventoryHandler = inventoryHandler;
        this.orderRepository = orderRepository;
    }

    public void processOrder(OrderRegisterDto orderRegisterDto) {
        String nameOfProduct = orderRegisterDto.nameOfProduct();
        Product product = getProductWithName(nameOfProduct);
        Order order = createOrder(product, orderRegisterDto);
        inventoryHandler.reduceQuantity(product, orderRegisterDto.quantity(), orderRegisterDto.orderAt());
        orderRepository.save(order);
    }

    private Product getProductWithName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException(INVALID_PRODUCT));
    }

    private Order createOrder(Product product, OrderRegisterDto orderRegisterDto) {
        long quantity = orderRegisterDto.quantity();
        long discountedPrice = calculateDiscountedPrice(product, orderRegisterDto);
        return new Order(product, quantity, discountedPrice);
    }

    private long calculateDiscountedPrice(Product product, OrderRegisterDto orderRegisterDto) {
        long quantity = orderRegisterDto.quantity();
        if (product.isPromotionAvailable(orderRegisterDto.orderAt())) {
            return product.getPromotionItem().countFreeQuantity(quantity) * product.getPrice();
        }
        return 0;
    }
}
