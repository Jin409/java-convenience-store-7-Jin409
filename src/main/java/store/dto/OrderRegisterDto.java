package store.dto;

import java.time.LocalDate;

public record OrderRegisterDto(
        String nameOfProduct,
        long quantity,
        LocalDate orderAt
) {
}
