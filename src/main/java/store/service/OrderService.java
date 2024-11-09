package store.service;


import static store.service.ErrorMessages.OrderService.INVALID_PRODUCT;

import java.util.List;
import store.dto.OrderRegisterDto;
import store.model.Product;
import store.model.repository.ProductRepository;
import store.service.product.InventoryHandler;

public class OrderService {
    private final ProductRepository productRepository;
    private final InventoryHandler inventoryHandler;

    public OrderService(ProductRepository productRepository, InventoryHandler inventoryHandler) {
        this.productRepository = productRepository;
        this.inventoryHandler = inventoryHandler;
    }

    public void processOrders(List<OrderRegisterDto> orderRegisterDtos) {
        for (OrderRegisterDto orderRegisterDto : orderRegisterDtos) {
            String nameOfProduct = orderRegisterDto.nameOfProduct();
            Product product = getProductWithName(nameOfProduct);
            inventoryHandler.reduceQuantity(product, orderRegisterDto.quantity(), orderRegisterDto.orderAt());
        }
    }

    private Product getProductWithName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException(INVALID_PRODUCT));
    }
}
