package store.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("사이다", 1000, 10L,
                new Promotion("반짝할인", 1, 1, LocalDate.of(2022, 10, 04), LocalDate.of(2022, 10, 10)));
    }

    @Test
    void 보유한_수량보다_더_많은_주문이_들어오는_경우_에외가_발생한다() {
        // given
        long soldQuantity = 13L;

        // when & then
        assertThatThrownBy(() -> product.reduceQuantity(soldQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.Product.NOT_ENOUGH_QUANTITY);

    }
}
