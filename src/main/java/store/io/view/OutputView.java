package store.io.view;

import java.util.List;
import store.dto.ProductDisplayDto;
import store.io.view.formatter.OutputFormatter;

public class OutputView {
    public static void displayAllInformationOfProducts(List<ProductDisplayDto> productDisplayDtos) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");

        List<String> information = OutputFormatter.formatInformationOfProducts(productDisplayDtos);
        information.forEach(System.out::println);
    }
}
