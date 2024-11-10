package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.model.promotion.Promotion;

public class PromotionTest {
    @Test
    void 할인기간에_주문날짜가_포함되는지_확인한다() {
        // given
        LocalDate endDate = LocalDate.of(2022, 2, 12);
        Promotion promotion = new Promotion("할인", 1, 1, endDate.minusDays(1), endDate);

        // when
        boolean applicable = promotion.isApplicable(endDate.plusDays(1).atStartOfDay());

        // then
        assertThat(applicable).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"1,1,4,4", "1,1,4,4", "1,1,5,6", "2,1,4,4", "2,1,5,6"})
    void 할인이_적용되는_경우의_수량을_반환한다(long quantityToBuy, long quantityToGet, long input, long expected) {
        // given
        LocalDate endDate = LocalDate.of(2022, 2, 12);
        Promotion promotion = new Promotion("할인", 2, 1, endDate.minusDays(1), endDate);

        // when
        long result = promotion.countQuantityToGet(input);

        // then
        assertThat(result).isEqualTo(expected);
    }
}
