package store.dto;

import java.time.LocalDateTime;

public record OrderRegisterDto(String nameOfProduct, long quantity, LocalDateTime orderAt) {
    public static OrderRegisterDto copyWithNewQuantity(OrderRegisterDto originDto, long newQuantity) {
        return new OrderRegisterDto(originDto.nameOfProduct, newQuantity, originDto.orderAt);
    }
}
