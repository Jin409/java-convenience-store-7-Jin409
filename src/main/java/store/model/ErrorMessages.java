package store.model;

public final class ErrorMessages {
    private ErrorMessages() {
    }

    public final class Product {
        private Product() {
        }

        public static final String NOT_ENOUGH_QUANTITY = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    }

    public final class StockItem {
        private StockItem() {
        }

        public static final String NOT_ENOUGH_QUANTITY = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    }
}
