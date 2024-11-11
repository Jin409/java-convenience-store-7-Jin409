package store.model;

public final class ModelErrorMessages {
    private ModelErrorMessages() {
    }

    public final class OfProduct {
        private OfProduct() {
        }

        public static final String NOT_ENOUGH_QUANTITY = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    }

    public final class OfStockItem {
        private OfStockItem() {
        }

        public static final String NOT_ENOUGH_QUANTITY = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    }
}
