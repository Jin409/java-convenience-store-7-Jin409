package store.config;

import store.controller.StoreControllerFacade;

public class AppConfig {
    public RepositoryConfig repositoryConfig() {
        return new RepositoryConfig();
    }

    public InventoryHandlerConfig inventoryHandlerConfig() {
        return new InventoryHandlerConfig();
    }

    public ServiceConfig serviceConfig() {
        return new ServiceConfig(repositoryConfig(), inventoryHandlerConfig());
    }

    public ControllerConfig controllerConfig() {
        return new ControllerConfig(serviceConfig());
    }

    public StoreControllerFacade storeControllerFacade() {
        return new StoreControllerFacade(controllerConfig().promotionController(),
                controllerConfig().productController(),
                controllerConfig().orderController(), controllerConfig().receiptController());
    }
}
