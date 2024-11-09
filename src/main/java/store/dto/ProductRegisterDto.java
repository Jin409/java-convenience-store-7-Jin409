package store.dto;

public record ProductRegisterDto(
        String name,
        long price,
        long quantity,
        String nameOfPromotion,
        boolean isPromotion
) {
}

