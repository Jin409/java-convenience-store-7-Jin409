package store.io.view.formatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import store.dto.ProductDisplayDto;
import store.dto.ProductDisplayDto.Stock;
import store.dto.ProductDisplayDto.Promotion;

public class OutputFormatter {

    private OutputFormatter() {
    }

    private static final String DECIMAL_FORMAT = "###,###";

    public static List<String> formatInformationOfProducts(List<ProductDisplayDto> productDisplayDtos) {
        List<String> results = new ArrayList<>();

        for (ProductDisplayDto productDisplayDto : productDisplayDtos) {
            StringJoiner stringJoiner = new StringJoiner(" ");

            stringJoiner.add("-");
            stringJoiner.add(productDisplayDto.getName());
            stringJoiner.add(new DecimalFormat(DECIMAL_FORMAT).format(productDisplayDto.getPrice()) + "원");

            if (productDisplayDto instanceof Stock) {
                String result = formatInformationOfDefaultProduct(stringJoiner, (Stock) productDisplayDto);
                results.add(result);
            }

            if (productDisplayDto instanceof ProductDisplayDto.Promotion) {
                String result = formatInformationOfPromotionProduct(stringJoiner, (Promotion) productDisplayDto);
                results.add(result);
            }
        }
        return results;
    }

    private static String formatInformationOfPromotionProduct(StringJoiner stringJoiner,
                                                              ProductDisplayDto.Promotion promotionProductDisplayDto) {
        formatQuantity(stringJoiner, promotionProductDisplayDto.getQuantityOfPromotion());
        stringJoiner.add(promotionProductDisplayDto.getNameOfPromotion());
        return stringJoiner.toString();
    }

    private static String formatInformationOfDefaultProduct(StringJoiner stringJoiner, Stock stockProductDisplayDto) {
        formatQuantity(stringJoiner, stockProductDisplayDto.getQuantity());
        return stringJoiner.toString();
    }

    private static StringJoiner formatQuantity(StringJoiner stringJoiner, long quantity) {
        if (quantity == 0) {
            stringJoiner.add("재고 없음");
            return stringJoiner;
        }
        stringJoiner.add(quantity + "개");
        return stringJoiner;
    }

}
