package store.service;

import java.util.List;
import store.dto.PromotionRegisterDto;
import store.model.Promotion;
import store.model.PromotionRepository;

public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public void savePromotions(List<PromotionRegisterDto> promotionRegisterDtos) {
        List<Promotion> promotions = promotionRegisterDtos.stream()
                .map(dto -> new Promotion(dto.name(), dto.quantityToBuy(), dto.quantityToGet(), dto.startDate(),
                        dto.endDate()))
                .toList();

        promotions.forEach(promotionRepository::save);

    }
}
