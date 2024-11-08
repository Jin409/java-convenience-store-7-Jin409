package store.handler;

import java.util.List;
import store.dto.OrderRegisterDto;
import store.io.view.InputView;
import store.io.view.formatter.InputFormatter;

public class InputHandler {
    public static List<OrderRegisterDto> getOrderFromCustomer() {
        String ordersFromCustomer = InputView.getOrderFromCustomer();
        return InputFormatter.fromInputToOrdersBeSaved(ordersFromCustomer);
    }
}
