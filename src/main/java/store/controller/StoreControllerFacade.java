package store.controller;

import store.handler.InputHandler;
import store.model.Orders;

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
        promotionController.savePromotions();
        productController.saveProducts();

        do {
            productController.displayInformation();
            orderController.processOrders();
            Orders orders = orderController.getProcessedOrders();
            receiptController.displayReceipt(orders);
        } while (InputHandler.askToContinue().meansTrue());
    }
}
