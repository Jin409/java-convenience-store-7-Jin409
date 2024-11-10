package store.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.model.promotion.Promotion;
import store.model.promotion.PromotionItem;

public class PromotionItemTest {
    private PromotionItem promotionItem;
    private long quantity;

    @BeforeEach
    void setUp() {
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        Promotion promotion = new Promotion("탄산 1+2", 1, 1, endDate.minusDays(1), endDate);
        this.quantity = 3;
        this.promotionItem = new PromotionItem(promotion, quantity);
    }

    @Test
    void 주문_수량이_보유_수량보다_많은_경우_남은_주문_수량을_반환한다() {
        // given
        long orderedQuantity = 4;

        // when
        long remainingAfterReduction = promotionItem.getRemainingAfterReduction(orderedQuantity);

        // then
        assertThat(remainingAfterReduction).isEqualTo(1);
    }

    @Test
    void 주문_수량이_보유_수량보다_적은_경우_0을_반환한다() {
        // given
        long orderedQuantity = 3;

        // when
        long remainingAfterReduction = promotionItem.getRemainingAfterReduction(orderedQuantity);

        // then
        assertThat(remainingAfterReduction).isEqualTo(0);
    }
}
