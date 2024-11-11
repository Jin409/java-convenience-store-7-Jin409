package store.io.view.validator;

import java.util.List;

public class InputValidator {
    private InputValidator() {
    }

    private static final String REGEX_OF_KOREAN = "^[가-힣]+$";
    private static final String REGEX_OF_INTEGER = "^[0-9]+$";

    public static void validateOrdersFromCustomer(List<String> orders) {
        for (String order : orders) {
            validateOrderFormat(order);
            validateOrderDashPresence(order);

            String[] replacedOrder = removeBracketsAndSplitOrder(order);
            validateOrderLength(replacedOrder);
            validateKoreanProductName(replacedOrder[0]);
            validateQuantity(replacedOrder[1]);
        }
    }

    private static void validateOrderFormat(String order) {
        if (hasInvalidFormat(order)) {
            throw new IllegalArgumentException(InputValidatorErrorMessages.INVALID_ORDER_FORMAT);
        }
    }

    private static void validateOrderDashPresence(String order) {
        if (doesNotHaveDash(order)) {
            throw new IllegalArgumentException(InputValidatorErrorMessages.ORDER_MISSING_DASH);
        }
    }

    private static String[] removeBracketsAndSplitOrder(String order) {
        return order.replaceAll("[\\[\\]]", "").split("-");
    }

    private static void validateOrderLength(String[] replacedOrder) {
        if (replacedOrder.length != 2) {
            throw new IllegalArgumentException(InputValidatorErrorMessages.INVALID_ORDER_FORMAT);
        }
    }

    private static void validateKoreanProductName(String productName) {
        if (isNotKorean(productName)) {
            throw new IllegalArgumentException(InputValidatorErrorMessages.INVALID_KOREAN_PRODUCT_NAME);
        }
    }

    private static void validateQuantity(String quantity) {
        if (isNotInteger(quantity)) {
            throw new IllegalArgumentException(InputValidatorErrorMessages.INVALID_QUANTITY);
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
