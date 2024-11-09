package store.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.config.AppConfig;
import store.dto.OrderRegisterDto;
import store.model.promotion.Promotion;
import store.model.promotion.PromotionType;

public class OrderServiceTest {

    private OrderService orderService;
    private LocalDate orderedAt;

    @BeforeEach
    void setUp() {
        AppConfig appConfig = new AppConfig();
        this.orderService = appConfig.orderService();
        this.orderedAt = LocalDate.of(2022, 10, 22);
    }

    @Test
    void 존재하지_않는_이름의_상품을_주문하고자_하는_경우_예외가_발생한다() {
        // given
        OrderRegisterDto orderRegisterDto = new OrderRegisterDto("사이다", 2L, orderedAt);

        // when & then
        assertThatThrownBy(() -> orderService.processOrder(orderRegisterDto)).isInstanceOf(
                IllegalArgumentException.class).hasMessage(ErrorMessages.OrderService.INVALID_PRODUCT);
    }

    @Test
    void 수량이_부족한_경우_에외가_발생한다() {
        // given
        String name = "사이다";
        long orderedQuantity = 2;
        OrderRegisterDto orderRegisterDto = new OrderRegisterDto(name, orderedQuantity, orderedAt);
        Promotion promotion = new Promotion("MD추천", 1, 1, orderedAt.minusDays(1), orderedAt.plusDays(1),
                PromotionType.RECOMMENDATION);

        // when & then
        assertThatThrownBy(() -> orderService.processOrder(orderRegisterDto)).isInstanceOf(
                IllegalArgumentException.class);
    }
}
