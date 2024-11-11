package store.service;

public final class ServiceErrorMessages {
    private ServiceErrorMessages() {
    }

    public final class OfProductService {
        private OfProductService() {
        }

        public static final String INVALID_PROMOTION_NAME = "[ERROR] 존재하지 않는 할인 행사입니다.";
    }

    public final class OfOrderService {
        private OfOrderService() {
        }

        public static final String INVALID_PRODUCT = "[ERROR] 존재하지 않는 상품명입니다";
    }

    public final class OfReceiptService {
        private OfReceiptService() {
        }

        public static final String NO_REGISTERED_RECEIPT = "[ERROR] 등록된 영수증이 없습니다.";
    }
}
