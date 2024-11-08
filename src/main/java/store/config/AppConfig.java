package store.config;

import store.controller.StoreController;
import store.model.ProductRepository;
import store.model.ProductRepositoryImpl;
import store.model.PromotionRepository;
import store.model.PromotionRepositoryImpl;
import store.service.ProductService;
import store.service.PromotionService;

public class AppConfig {

    public PromotionRepository promotionRepository() {
        return new PromotionRepositoryImpl();
    }

    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    public PromotionService promotionService() {
        return new PromotionService(promotionRepository());
    }

    public ProductService productService() {
        return new ProductService(productRepository(), promotionRepository());
    }

    public StoreController storeController() {
        return new StoreController(promotionService(), productService());
    }
}
