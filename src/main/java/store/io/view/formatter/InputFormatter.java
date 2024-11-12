package store.io.view.formatter;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;
import store.dto.OrderRegisterDto;
import store.io.view.Constants;

public class InputFormatter {
    public static List<OrderRegisterDto> fromInputToOrdersBeSaved(String input) {
        List<OrderRegisterDto> orderRegisterDtos = new ArrayList<>();

        for (String order : input.split(Constants.DELIMITER)) {
            String[] split = order.replaceAll("[\\[\\]]", "").split("-");

            String nameOfProduct = split[0];
            long quantity = Long.parseLong(split[1]);
            orderRegisterDtos.add(new OrderRegisterDto(nameOfProduct, quantity, DateTimes.now()));
        }

        return orderRegisterDtos;
    }
}
