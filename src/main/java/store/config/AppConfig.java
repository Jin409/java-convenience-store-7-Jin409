package store.config;

import store.controller.StoreController;
import store.model.ProductRepository;
import store.model.PromotionRepository;
import store.service.ProductService;
import store.service.PromotionService;

public class AppConfig {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public AppConfig(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public PromotionService promotionService() {
        return new PromotionService(promotionRepository);
    }

    public ProductService productService() {
        return new ProductService(productRepository, promotionRepository);
    }

    public StoreController storeController() {
        return new StoreController(promotionService(), productService());
    }
}
