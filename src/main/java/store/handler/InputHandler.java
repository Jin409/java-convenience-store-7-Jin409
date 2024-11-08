package store.handler;

import java.util.ArrayList;
import java.util.List;
import store.dto.OrderRegisterDto;
import store.io.view.InputView;

public class InputHandler {
    public static List<OrderRegisterDto> getOrderFromCustomer() {
        String ordersFromCustomer = InputView.getOrderFromCustomer();

        List<OrderRegisterDto> orderRegisterDtos = new ArrayList<>();

        for (String order : ordersFromCustomer.split(",")) {
            String[] split = order.replaceAll("[\\[\\]]", "").split("-");

            String nameOfProduct = split[0];
            int quantity = Integer.parseInt(split[1]);

            orderRegisterDtos.add(new OrderRegisterDto(nameOfProduct, quantity));
        }

        return orderRegisterDtos;
    }
}
