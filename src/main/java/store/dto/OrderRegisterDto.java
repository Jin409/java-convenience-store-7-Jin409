package store.dto;

import java.time.LocalDate;

public record OrderRegisterDto(String nameOfProduct, long quantity, LocalDate orderAt) {
    public static OrderRegisterDto from(OrderRegisterDto originDto, long missingOrderQuantity) {
        return new OrderRegisterDto(originDto.nameOfProduct, originDto.quantity + missingOrderQuantity,
                originDto.orderAt);
    }
}
