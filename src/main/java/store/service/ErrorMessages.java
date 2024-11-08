package store.service;

public final class ErrorMessages {
    private ErrorMessages() {
    }

    public final class ProductService {
        private ProductService() {
        }

        public static final String INVALID_PROMOTION_NAME = "[ERROR] 존재하지 않는 할인 행사입니다.";
    }

    public final class OrderService {
        private OrderService() {
        }

        public static final String INVALID_PRODUCT = "[ERROR] 존재하지 않는 상품명입니다";
    }
}
