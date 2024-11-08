package store.io.view.formatter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import store.dto.ProductDisplayDto;
import store.dto.ProductDisplayDto.Default;
import store.dto.ProductDisplayDto.Promotion;

public class OutputFormatter {

    private OutputFormatter() {
    }

    public static List<String> formatInformationOfProducts(List<ProductDisplayDto> productDisplayDtos) {
        List<String> results = new ArrayList<>();

        for (ProductDisplayDto productDisplayDto : productDisplayDtos) {
            StringJoiner stringJoiner = new StringJoiner(" ");

            stringJoiner.add("-");
            stringJoiner.add(productDisplayDto.getName());
            stringJoiner.add(productDisplayDto.getPrice() + "원");

            if (productDisplayDto instanceof ProductDisplayDto.Default) {
                String result = formatInformationOfDefaultProduct(stringJoiner, (Default) productDisplayDto);
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
        stringJoiner.add(promotionProductDisplayDto.getQuantityOfPromotion() + "개");
        stringJoiner.add(promotionProductDisplayDto.getNameOfPromotion());

        return stringJoiner.toString();
    }

    private static String formatInformationOfDefaultProduct(StringJoiner stringJoiner,
                                                            ProductDisplayDto.Default defaultProductDisplayDto) {
        stringJoiner.add(defaultProductDisplayDto.getQuantity() + "개");
        return stringJoiner.toString();
    }

}
