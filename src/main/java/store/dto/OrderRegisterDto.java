package store.dto;

public record OrderRegisterDto(
        String nameOfProduct,
        long quantity
) {
}
