package store.config;

import store.service.MembershipDiscountService;
import store.service.OrderService;
import store.service.PromotionService;
import store.service.ReceiptService;
import store.service.product.ProductService;

public class ServiceConfig {
    private final RepositoryConfig repositoryConfig;
    private final InventoryHandlerConfig inventoryHandlerConfig;

    public ServiceConfig(RepositoryConfig repositoryConfig, InventoryHandlerConfig inventoryHandlerConfig) {
        this.repositoryConfig = repositoryConfig;
        this.inventoryHandlerConfig = inventoryHandlerConfig;
    }

    public PromotionService promotionService() {
        return new PromotionService(repositoryConfig.promotionRepository());
    }

    public ProductService productService() {
        return new ProductService(repositoryConfig.productRepository(), repositoryConfig.promotionRepository());
    }

    public OrderService orderService() {
        return new OrderService(repositoryConfig.productRepository(), inventoryHandlerConfig.inventoryHandler(),
                repositoryConfig.orderRepository());
    }

    public MembershipDiscountService membershipDiscountService() {
        return new MembershipDiscountService(repositoryConfig.orderRepository());
    }

    public ReceiptService receiptService() {
        return new ReceiptService();
    }
}
