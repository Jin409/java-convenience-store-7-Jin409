package store.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.dto.PromotionRegisterDto;
import store.model.promotion.PromotionType;

public class PromotionTypeTest {
    @Nested
    @DisplayName("할인 타입을 찾고자 하는 경우")
    class TestFindType {
        @Test
        void 추가_수량_증정에_대해_알맞은_프로모션_타입을_반환한다() {
            // given
            PromotionRegisterDto promotionRegisterDto = new PromotionRegisterDto("탄산2+1", 2, 1,
                    LocalDate.of(2022, 10, 1), LocalDate.of(2022, 10, 2));

            // when
            PromotionType promotionType = PromotionType.findType(promotionRegisterDto);

            // then
            assertAll(() -> assertThat(promotionType).isNotNull(),
                    () -> assertThat(promotionType).isEqualTo(PromotionType.N_PLUS_M));
        }

        @Test
        void 추천_상품에_대해_알맞은_프로모션_타입을_반환한다() {
            // given
            PromotionRegisterDto promotionRegisterDto = new PromotionRegisterDto("MD추천", 1, 1,
                    LocalDate.of(2022, 10, 1), LocalDate.of(2022, 10, 2));

            // when
            PromotionType promotionType = PromotionType.findType(promotionRegisterDto);

            // then
            assertAll(() -> assertThat(promotionType).isNotNull(),
                    () -> assertThat(promotionType).isEqualTo(PromotionType.RECOMMENDATION));
        }

        @Test
        void 할인에_대해_알맞은_프로모션_타입을_반환한다() {
            // given
            PromotionRegisterDto promotionRegisterDto = new PromotionRegisterDto("반짝할인", 1, 1,
                    LocalDate.of(2022, 10, 1), LocalDate.of(2022, 10, 2));

            // when
            PromotionType promotionType = PromotionType.findType(promotionRegisterDto);

            // then
            assertAll(() -> assertThat(promotionType).isNotNull(),
                    () -> assertThat(promotionType).isEqualTo(PromotionType.DISCOUNT));
        }

        @Test
        void 조건에_알맞는_프로모션_타입이_없는_경우_null을_반환한다() {
            // given
            PromotionRegisterDto promotionRegisterDto = new PromotionRegisterDto("속하지않는프로모션", 1, 1,
                    LocalDate.of(2022, 10, 1), LocalDate.of(2022, 10, 2));

            // when
            PromotionType promotionType = PromotionType.findType(promotionRegisterDto);

            // then
            assertThat(promotionType).isNull();
        }
    }
}
