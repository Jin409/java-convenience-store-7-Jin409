package store.io.view.validator;

import java.util.List;

public class InputValidator {
    private InputValidator() {
    }

    private static final String REGEX_OF_KOREAN = "^[가-힣]+$";
    private static final String REGEX_OF_INTEGER = "^[0-9]+$";

    public static void validateOrdersFromCustomer(List<String> orders) {
        for (String order : orders) {
            if (hasInvalidFormat(order)) {
                throw new IllegalArgumentException(InputValidatorErrorMessages.INVALID_ORDER_FORMAT);
            }

            if (doesNotHaveDash(order)) {
                throw new IllegalArgumentException(InputValidatorErrorMessages.ORDER_MISSING_DASH);
            }

            String[] replacedOrder = order.replaceAll("[\\[\\]]", "").split("-");

            if (replacedOrder.length != 2) {
                throw new IllegalArgumentException(InputValidatorErrorMessages.INVALID_ORDER_FORMAT);
            }

            if (isNotKorean(replacedOrder[0])) {
                throw new IllegalArgumentException(InputValidatorErrorMessages.INVALID_KOREAN_PRODUCT_NAME);
            }

            if (isNotInteger(replacedOrder[1])) {
                throw new IllegalArgumentException(InputValidatorErrorMessages.INVALID_QUANTITY);
            }
        }
    }

    private static boolean doesNotHaveDash(String input) {
        return !input.contains("-");
    }

    private static boolean hasInvalidFormat(String input) {
        return !input.startsWith("[") || !input.endsWith("]");
    }

    private static boolean isNotInteger(String input) {
        return !input.matches(REGEX_OF_INTEGER);
    }

    private static boolean isNotKorean(String input) {
        return !input.matches(REGEX_OF_KOREAN);
    }
}

