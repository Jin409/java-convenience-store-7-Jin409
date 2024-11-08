package store.io;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import store.dto.ProductRegisterDto;
import store.dto.PromotionRegisterDto;

public class MarkDownReader {
    public static List<PromotionRegisterDto> readPromotions() {
        try {
            List<PromotionRegisterDto> promotionRegisterDtos = new ArrayList<>();
            Scanner promotionsScanner = new Scanner(new File("src/main/resources/promotions.md"));
            promotionsScanner.nextLine();

            while (promotionsScanner.hasNextLine()) {

                String[] str = promotionsScanner.nextLine().split(",");
                PromotionRegisterDto promotionRegisterDto = new PromotionRegisterDto(str[0], Integer.parseInt(str[1]),
                        Integer.parseInt(str[2]),
                        LocalDate.parse(str[3]), LocalDate.parse(str[4]));
                promotionRegisterDtos.add(promotionRegisterDto);
            }

            return promotionRegisterDtos;
        } catch (Exception ignored) {
        }
        return null;
    }

    public static List<ProductRegisterDto> readProducts() {
        try {

            List<ProductRegisterDto> productRegisterDtos = new ArrayList<>();
            Scanner promotionsScanner = new Scanner(new File("src/main/resources/products.md"));
            promotionsScanner.nextLine();

            while (promotionsScanner.hasNextLine()) {

                String[] str = promotionsScanner.nextLine().split(",");
                String nameOfPromotion = str[3];

                if (nameOfPromotion.equals("null")) {
                    ProductRegisterDto productRegisterDto = new ProductRegisterDto(str[0], Long.parseLong(str[1]),
                            Integer.parseInt(str[2]),
                            str[3], false);
                    productRegisterDtos.add(productRegisterDto);
                    continue;
                }
                ProductRegisterDto productRegisterDto = new ProductRegisterDto(str[0], Long.parseLong(str[1]),
                        Integer.parseInt(str[2]),
                        str[3], true);
                productRegisterDtos.add(productRegisterDto);
            }

            return productRegisterDtos;

        } catch (Exception ignored) {
        }
        return null;
    }
}
