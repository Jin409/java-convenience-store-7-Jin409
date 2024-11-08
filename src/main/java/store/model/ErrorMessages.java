package store.model;

public final class ErrorMessages {
    private ErrorMessages() {
    }

    public final class Product {
        private Product() {
        }

        public static final String NOT_ENOUGH_QUANTITY = "[ERROR] 주문 수량이 보유 수량보다 많습니다.";
    }
}
