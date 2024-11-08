package store.io.view;

import java.util.List;
import java.util.StringJoiner;
import store.dto.ProductDisplayDto;

public class OutputView {
    public static void displayAllInformationOfProducts(List<ProductDisplayDto> productDisplayDtos) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");

        for (ProductDisplayDto productDisplayDto : productDisplayDtos) {
            StringJoiner stringJoiner = new StringJoiner(" ");

            stringJoiner.add("-");
            stringJoiner.add(productDisplayDto.nameOfProduct());
            stringJoiner.add(productDisplayDto.price() + "원");
            stringJoiner.add(productDisplayDto.quantity() + "개");

            if (productDisplayDto.nameOfPromotion() != null) {
                stringJoiner.add(productDisplayDto.nameOfPromotion());
            }

            System.out.println(stringJoiner);
        }
    }
}
