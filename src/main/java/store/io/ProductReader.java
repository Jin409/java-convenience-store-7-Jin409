package store.io;

import java.util.ArrayList;
import java.util.List;
import store.dto.ProductRegisterDto;
import store.handler.ErrorHandler;

public class ProductReader {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static final String DELIMITER = ",";

    public static List<ProductRegisterDto> readProducts() {

        List<String[]> productData = FileReader.readMarkdownFile(PRODUCTS_FILE_PATH, DELIMITER);

        List<ProductRegisterDto> productRegisterDtos = new ArrayList<>();
        for (String[] productDatum : productData) {
            try {
                productRegisterDtos.add(getProductRegisterDto(productDatum));
            } catch (Exception e) {
                ErrorHandler.handle(e);
            }
        }
        return productRegisterDtos;
    }

    private static ProductRegisterDto getProductRegisterDto(String[] productDatum) {
        boolean hasPromotion = true;
        String promotionName = productDatum[3];

        if (promotionName.equals("null")) {
            hasPromotion = false;
        }
        return new ProductRegisterDto(productDatum[0], Long.parseLong(productDatum[1]),
                Integer.parseInt(productDatum[2]), productDatum[3], hasPromotion);
    }
}
