package store.controller;

import java.util.List;
import store.dto.PromotionRegisterDto;
import store.io.PromotionReader;
import store.service.PromotionService;

public class PromotionController {
    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    public void savePromotions() {
        List<PromotionRegisterDto> promotionRegisterDtos = PromotionReader.readPromotions();
        promotionService.savePromotions(promotionRegisterDtos);
    }
}
