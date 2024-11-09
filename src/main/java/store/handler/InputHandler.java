package store.handler;

import java.util.List;
import store.controller.AnswerSign;
import store.dto.OrderRegisterDto;
import store.io.view.Constants;
import store.io.view.InputView;
import store.io.view.formatter.InputFormatter;
import store.io.view.validator.InputValidator;
import store.service.PromotionApplyResult;

public class InputHandler {
    public static List<OrderRegisterDto> getOrderFromCustomer() {
        String ordersFromCustomer = InputView.getOrderFromCustomer();
        InputValidator.validateOrdersFromCustomer(List.of(ordersFromCustomer.split(Constants.DELIMITER)));
        return InputFormatter.fromInputToOrdersBeSaved(ordersFromCustomer);
    }

    public static AnswerSign askToAddMoreItems(PromotionApplyResult applyResult) {
        String answer = InputView.askToAddMoreItems(applyResult);
        return AnswerSign.getBySign(answer);
    }

    public static AnswerSign askToBuyWithoutPromotion(PromotionApplyResult applyResult) {
        String answer = InputView.askToBuyWithoutPromotion(applyResult);
        return AnswerSign.getBySign(answer);
    }

    public static AnswerSign askToApplyMembershipDiscount() {
        return AnswerSign.getBySign(InputView.askToApplyMembershipDiscount());
    }

    public static AnswerSign askToLeave() {
        return AnswerSign.getBySign(InputView.askToLeave());
    }
}