package store.service;

import java.util.List;
import store.dto.PromotionRegisterDto;
import store.model.promotion.Promotion;
import store.model.promotion.PromotionType;
import store.model.repository.PromotionRepository;

public class PromotionService {
    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public void savePromotions(List<PromotionRegisterDto> promotionRegisterDtos) {
        List<Promotion> promotions = promotionRegisterDtos.stream()
                .map(dto -> new Promotion(dto.name(), dto.quantityToBuy(), dto.quantityToGet(), dto.startDate(),
                        dto.endDate(), PromotionType.findType(dto))).toList();
        promotions.forEach(promotionRepository::save);
    }
}
