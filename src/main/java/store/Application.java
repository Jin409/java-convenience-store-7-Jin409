package store;

import store.config.AppConfig;
import store.controller.StoreController;
import store.model.ProductRepository;
import store.model.PromotionRepository;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig(
                new ProductRepository(),
                new PromotionRepository()
        );

        StoreController storeController = appConfig.storeController();
        storeController.run();
    }
}
