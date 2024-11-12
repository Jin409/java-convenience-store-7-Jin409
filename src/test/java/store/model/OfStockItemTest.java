package store.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OfStockItemTest {

    private StockItem stockItem;
    private long quantity;

    @BeforeEach
    void setUp() {
        this.quantity = 3;
        this.stockItem = new StockItem(quantity);
    }

    @Test
    void 주문_수량이_보유_수량보다_많은_경우_예외가_발생한다() {
        // given
        long orderedQuantity = 4;

        // when & then
        assertThatThrownBy(
                () -> stockItem.reduceQuantity(orderedQuantity)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
