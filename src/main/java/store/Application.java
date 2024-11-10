package store;

import store.config.AppConfig;
import store.controller.StoreControllerFacade;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();

        StoreControllerFacade storeControllerFacade = appConfig.storeControllerFacade();
        storeControllerFacade.run();
    }
}
