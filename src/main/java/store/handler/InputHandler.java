package store.handler;

import java.util.List;
import store.dto.OrderRegisterDto;
import store.io.view.Constants;
import store.io.view.InputView;
import store.io.view.formatter.InputFormatter;
import store.io.view.validator.InputValidator;

public class InputHandler {
    public static List<OrderRegisterDto> getOrderFromCustomer() {
        String ordersFromCustomer = InputView.getOrderFromCustomer();
        InputValidator.validateOrdersFromCustomer(List.of(ordersFromCustomer.split(Constants.DELIMITER)));
        return InputFormatter.fromInputToOrdersBeSaved(ordersFromCustomer);
    }
}