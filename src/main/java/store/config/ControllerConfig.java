package store.config;

import store.controller.OrderController;
import store.controller.ProductController;
import store.controller.PromotionController;
import store.controller.ReceiptController;

public class ControllerConfig {
    private final ServiceConfig serviceConfig;

    public ControllerConfig(ServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    public PromotionController promotionController() {
        return new PromotionController(serviceConfig.promotionService());
    }

    public ProductController productController() {
        return new ProductController(serviceConfig.productService());
    }

    public OrderController orderController() {
        return new OrderController(serviceConfig.orderService(), serviceConfig.productService(),
                serviceConfig.promotionService());
    }

    public ReceiptController receiptController() {
        return new ReceiptController(serviceConfig.receiptService(), serviceConfig.membershipDiscountService());
    }

}
