package store.dto;

import java.time.LocalDate;

public record PromotionRegisterDto(
        String name,
        int quantityToBuy,
        int quantityToGet,
        LocalDate startDate,
        LocalDate endDate
) {
}
