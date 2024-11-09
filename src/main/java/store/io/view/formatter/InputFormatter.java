package store.io.view.formatter;

import java.util.ArrayList;
import java.util.List;
import store.dto.OrderRegisterDto;

public class InputFormatter {
    public static List<OrderRegisterDto> fromInputToOrdersBeSaved(String input) {
        List<OrderRegisterDto> orderRegisterDtos = new ArrayList<>();

        for (String order : input.split(",")) {
            String[] split = order.replaceAll("[\\[\\]]", "").split("-");

            String nameOfProduct = split[0];
            long quantity = Long.parseLong(split[1]);

            orderRegisterDtos.add(new OrderRegisterDto(nameOfProduct, quantity));
        }

        return orderRegisterDtos;
    }
}