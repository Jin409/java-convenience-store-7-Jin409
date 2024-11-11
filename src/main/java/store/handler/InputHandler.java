package store.handler;

import java.util.List;
import java.util.function.Supplier;
import store.io.view.sign.AnswerSign;
import store.dto.OrderRegisterDto;
import store.io.view.Constants;
import store.io.view.InputView;
import store.io.view.formatter.InputFormatter;
import store.io.view.validator.InputValidator;
import store.dto.PromotionApplyResult;

public class InputHandler {
    public static List<OrderRegisterDto> getOrderFromCustomer() {
        String ordersFromCustomer = retryOnInvalidInput(InputView::getOrderFromCustomer);
        InputValidator.validateOrdersFromCustomer(List.of(ordersFromCustomer.split(Constants.DELIMITER)));
        return InputFormatter.fromInputToOrdersBeSaved(ordersFromCustomer);
    }

    public static AnswerSign askToAddMoreItems(PromotionApplyResult applyResult) {
        return retryOnInvalidInput(() -> {
            String answer = InputView.askToAddMoreItems(applyResult);
            return AnswerSign.getBySign(answer);
        });
    }

    public static AnswerSign askToBuyWithoutPromotion(PromotionApplyResult applyResult) {
        return retryOnInvalidInput(() -> {
            String answer = InputView.askToBuyWithoutPromotion(applyResult);
            return AnswerSign.getBySign(answer);
        });
    }

    public static AnswerSign askToApplyMembershipDiscount() {
        return retryOnInvalidInput(() -> AnswerSign.getBySign(InputView.askToApplyMembershipDiscount()));
    }

    public static AnswerSign askToContinue() {
        return retryOnInvalidInput(() -> AnswerSign.getBySign(InputView.askToContinue()));
    }

    private static <T> T retryOnInvalidInput(Supplier<T> inputSupplier) {
        while (true) {
            try {
                return inputSupplier.get();
            } catch (IllegalArgumentException e) {
                ErrorHandler.handle(e);
            }
        }
    }
}