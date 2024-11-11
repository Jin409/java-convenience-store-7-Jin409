package store.io.view.validator;

public final class InputValidatorErrorMessages {
    private InputValidatorErrorMessages() {
    }

    public static final String INVALID_ORDER_FORMAT = "[ERROR] 잘못된 주문 형식입니다. [상품명-수량],[상품명-수량] 과 같은 형태여야 합니다.";
    public static final String ORDER_MISSING_DASH = "[ERROR] 주문 형식에 '-' 기호가 포함되어야 합니다.";
    public static final String INVALID_KOREAN_PRODUCT_NAME = "[ERROR] 상품명은 한글로 작성되어야 합니다.";
    public static final String INVALID_QUANTITY = "[ERROR] 수량은 정수로 입력해야 합니다.";
}
