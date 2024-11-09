package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import store.model.promotion.Promotion;
import store.model.promotion.PromotionType;

public class PromotionTest {
    @Test
    void 할인기간에_주문날짜가_포함되는지_확인한다() {
        // given
        LocalDate endDate = LocalDate.of(2022, 2, 12);
        Promotion promotion = new Promotion("할인", 1, 1, endDate.minusDays(1), endDate, PromotionType.DISCOUNT);

        // when
        boolean applicable = promotion.isApplicable(endDate.plusDays(1));

        // then
        assertThat(applicable).isFalse();
    }
}
