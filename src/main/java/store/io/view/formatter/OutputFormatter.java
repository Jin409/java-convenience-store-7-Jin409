package store.io.view.formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import store.dto.ProductDisplayDto;

public class OutputFormatter {

    private OutputFormatter() {
    }

    public static List<String> formatInformationOfProducts(List<ProductDisplayDto> productDisplayDtos) {
        List<String> result = new ArrayList<>();

        for (ProductDisplayDto productDisplayDto : productDisplayDtos) {
            StringJoiner stringJoiner = new StringJoiner(" ");

            stringJoiner.add("-");
            stringJoiner.add(productDisplayDto.nameOfProduct());
            stringJoiner.add(productDisplayDto.price() + "원");
            stringJoiner.add(productDisplayDto.quantity() + "개");

            if (productDisplayDto.nameOfPromotion() != null) {
                stringJoiner.add(productDisplayDto.nameOfPromotion());
            }

            result.add(stringJoiner.toString());
        }

        return result;
    }
}
