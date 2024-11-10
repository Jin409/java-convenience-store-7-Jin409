package store.controller;

import store.io.view.OutputView;
import store.model.Orders;
import store.service.ReceiptService;

public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    public void displayReceipt(Orders orders) {
        OutputView.displayReceipt(receiptService.getReceipt(orders));
    }
}
