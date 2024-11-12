package store.io.reader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import store.dto.PromotionRegisterDto;
import store.handler.ErrorHandler;

public class PromotionReader {
    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    private static final String DELIMITER = ",";

    public static List<PromotionRegisterDto> readPromotions() {
        List<String[]> promotionData = FileReader.readMarkdownFile(PROMOTIONS_FILE_PATH, DELIMITER);

        List<PromotionRegisterDto> promotionRegisterDtos = new ArrayList<>();
        for (String[] promotionDatum : promotionData) {
            try {
                promotionRegisterDtos.add(getPromotionDto(promotionDatum));
            } catch (Exception e) {
                ErrorHandler.handle(e);
            }
        }
        return promotionRegisterDtos;
    }

    private static PromotionRegisterDto getPromotionDto(String[] promotionDatum) {
        return new PromotionRegisterDto(promotionDatum[0], Integer.parseInt(promotionDatum[1]),
                Integer.parseInt(promotionDatum[2]), LocalDate.parse(promotionDatum[3]),
                LocalDate.parse(promotionDatum[4]));
    }
}