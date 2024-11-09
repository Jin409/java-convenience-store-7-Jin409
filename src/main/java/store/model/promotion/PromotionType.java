package store.model.promotion;

import java.util.Arrays;
import java.util.function.Predicate;
import store.dto.PromotionRegisterDto;

public enum PromotionType {
    N_PLUS_M((dto) -> dto.quantityToBuy() != dto.quantityToGet()),
    RECOMMENDATION(dto -> dto.name().contains("추천")),
    DISCOUNT(dto -> dto.name().contains("할인"));

    private final Predicate<PromotionRegisterDto> condition;

    PromotionType(Predicate<PromotionRegisterDto> condition) {
        this.condition = condition;
    }

    public static PromotionType findType(PromotionRegisterDto dto) {
        return Arrays.asList(PromotionType.values()).stream()
                .filter(type -> type.condition.test(dto))
                .findAny()
                .orElse(null);
    }
}
