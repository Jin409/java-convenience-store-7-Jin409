package store.controller;

import store.handler.InputHandler;
import store.io.view.OutputView;
import store.model.Receipt;
import store.service.MembershipDiscountService;
import store.service.ReceiptService;

public class ReceiptController {
    private final ReceiptService receiptService;
    private final MembershipDiscountService membershipDiscountService;

    public ReceiptController(ReceiptService receiptService, MembershipDiscountService membershipDiscountService) {
        this.receiptService = receiptService;
        this.membershipDiscountService = membershipDiscountService;
    }

    public Receipt getReceipt() {
        AnswerSign answerSign = InputHandler.askToApplyMembershipDiscount();
        if (answerSign.meansTrue()) {
            return membershipDiscountService.getOrdersWithMembershipDiscount();
        }
        return receiptService.createReceiptWithoutDiscount();
    }

    public void displayReceipt(Receipt receipt) {
        OutputView.displayReceipt(receiptService.getReceipt(receipt));
    }
}
