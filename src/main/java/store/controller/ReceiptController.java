package store.controller;

import store.handler.InputHandler;
import store.io.view.sign.AnswerSign;
import store.io.view.OutputView;
import store.service.MembershipDiscountService;
import store.service.ReceiptService;

public class ReceiptController {
    private final ReceiptService receiptService;
    private final MembershipDiscountService membershipDiscountService;

    public ReceiptController(ReceiptService receiptService, MembershipDiscountService membershipDiscountService) {
        this.receiptService = receiptService;
        this.membershipDiscountService = membershipDiscountService;
    }

    public void createReceipt() {
        AnswerSign answerSign = InputHandler.askToApplyMembershipDiscount();

        long discountedAmount = 0;
        if (answerSign.meansTrue()) {
            discountedAmount = membershipDiscountService.calculateDiscountedAmount();
        }
        receiptService.createReceipt(discountedAmount);
    }

    public void displayReceipt() {
        OutputView.displayReceipt(receiptService.getRecentReceipt());
    }
}
