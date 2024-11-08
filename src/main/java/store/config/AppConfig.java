package store.config;

import store.controller.StoreController;
import store.model.repository.OrderRepository;
import store.model.repository.OrderRepositoryImpl;
import store.model.repository.ProductRepository;
import store.model.repository.ProductRepositoryImpl;
import store.model.repository.PromotionRepository;
import store.model.repository.PromotionRepositoryImpl;
import store.service.OrderService;
import store.service.ProductService;
import store.service.PromotionService;

public class AppConfig {

    public PromotionRepository promotionRepository() {
        return new PromotionRepositoryImpl();
    }

    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    public OrderRepository orderRepository() {
        return new OrderRepositoryImpl();
    }

    public PromotionService promotionService() {
        return new PromotionService(promotionRepository());
    }

    public ProductService productService() {
        return new ProductService(productRepository(), promotionRepository());
    }

    public OrderService orderService() {
        return new OrderService(orderRepository(), productRepository());
    }

    public StoreController storeController() {
        return new StoreController(promotionService(), productService(), orderService());
    }
}
