package store.dto;

import java.time.LocalDate;

public record OrderRegisterDto(String nameOfProduct, long quantity, LocalDate orderAt) {
    public static OrderRegisterDto copyWithNewQuantity(OrderRegisterDto originDto, long newQuantity) {
        return new OrderRegisterDto(originDto.nameOfProduct, newQuantity, originDto.orderAt);
    }
}
