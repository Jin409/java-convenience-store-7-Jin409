package store.config;

import store.controller.StoreController;
import store.model.repository.OrderRepository;
import store.model.repository.OrderRepositoryImpl;
import store.model.repository.ProductRepository;
import store.model.repository.ProductRepositoryImpl;
import store.model.repository.PromotionRepository;
import store.model.repository.PromotionRepositoryImpl;
import store.service.MembershipDiscountService;
import store.service.OrderService;
import store.service.PromotionService;
import store.service.product.InventoryHandler;
import store.service.product.ProductService;
import store.service.product.PromotionItemInventoryHandler;
import store.service.product.StockItemInventoryHandler;

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

    public InventoryHandler inventoryHandler() {
        PromotionItemInventoryHandler promotionItemQuantityManagingHandler = new PromotionItemInventoryHandler();
        StockItemInventoryHandler stockItemQuantityManagingHandler = new StockItemInventoryHandler();

        promotionItemQuantityManagingHandler.setNext(stockItemQuantityManagingHandler);

        return promotionItemQuantityManagingHandler;
    }

    public PromotionService promotionService() {
        return new PromotionService(promotionRepository());
    }

    public ProductService productService() {
        return new ProductService(productRepository(), promotionRepository());
    }

    public OrderService orderService() {
        return new OrderService(productRepository(), inventoryHandler(), orderRepository());
    }

    public MembershipDiscountService membershipDiscountService() {
        return new MembershipDiscountService(orderRepository());
    }

    public StoreController storeController() {
        return new StoreController(promotionService(), productService(), orderService(), membershipDiscountService());
    }
}
