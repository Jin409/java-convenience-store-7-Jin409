package store.controller;

import store.handler.ErrorHandler;
import store.handler.InputHandler;

public class StoreControllerFacade {
    private final PromotionController promotionController;
    private final ProductController productController;
    private final OrderController orderController;
    private final ReceiptController receiptController;

    public StoreControllerFacade(PromotionController promotionController, ProductController productController,
                                 OrderController orderController, ReceiptController receiptController) {
        this.promotionController = promotionController;
        this.productController = productController;
        this.orderController = orderController;
        this.receiptController = receiptController;
    }

    public void run() {
        try {
            readyToOpen();

            do {
                productController.displayInformation();
                orderController.processOrders();
                displayReceipt();
            } while (InputHandler.askToContinue().meansTrue());
        } catch (Exception e) {
            ErrorHandler.handle(e);
        }
    }

    private void readyToOpen() {
        promotionController.savePromotions();
        productController.saveProducts();
    }

    private void displayReceipt() {
        receiptController.createReceipt();
        receiptController.displayReceipt();
    }
}
