package store.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.config.AppConfig;
import store.dto.OrderRegisterDto;
import store.model.Product;
import store.model.StockItem;
import store.model.promotion.Promotion;
import store.model.promotion.PromotionItem;
import store.model.repository.OrderRepositoryImpl;
import store.model.repository.ProductRepository;

public class OrderServiceTest {

    private OrderService orderService;
    private LocalDateTime orderedAt;
    private AppConfig appConfig;

    @BeforeEach
    void setUp() {
        this.appConfig = new AppConfig();
        this.orderService = appConfig.orderService();
        this.orderedAt = LocalDateTime.of(2022, 2, 10, 2, 10);
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

        // when & then
        assertThatThrownBy(() -> orderService.processOrder(orderRegisterDto)).isInstanceOf(
                IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("알맞은 재고에 대한 주문인지 확인할 때")
    class TestApplyPromotion {
        private ProductRepository productRepository;
        private Promotion promotion;
        private LocalDateTime orderedAt;
        private OrderService orderServiceWithCustom;

        @BeforeEach
        void setUp() {
            orderedAt = LocalDate.of(2022, 10, 22).atStartOfDay();
            promotion = new Promotion("2+1", 2, 1, orderedAt.toLocalDate().minusDays(1),
                    orderedAt.toLocalDate().plusDays(1));
            productRepository = new ProductRepository() {
                @Override
                public void save(Product product) {
                    return;
                }

                @Override
                public List<Product> findAll() {
                    return List.of();
                }

                @Override
                public Optional<Product> findByName(String name) {
                    return Optional.of(new Product("사이다", 1000, new StockItem(1), new PromotionItem(promotion, 10)));
                }
            };
            orderServiceWithCustom = new OrderService(productRepository, appConfig.inventoryHandler(),
                    new OrderRepositoryImpl());
        }

        @Test
        void 프로모션이_적용되어_추가로_가져와야_하는_수량이_있는_경우_알맞은_형태를_반환한다() {
            // given
            OrderRegisterDto orderRegisterDto = new OrderRegisterDto("사이다", 2, orderedAt);

            // when
            PromotionApplyResult promotionApplyResult = orderServiceWithCustom.applyPromotion(orderRegisterDto);

            // then
            assertAll(() -> assertThat(promotionApplyResult.hasMissingOrderQuantity()).isTrue(),
                    () -> assertThat(promotionApplyResult.getMissingOrderQuantity()).isEqualTo(1));
        }

        @Test
        void 프로모션_재고가_부족해_정가로_결제해야_하는_경우_알맞은_형태를_반환한다() {
            // given
            OrderRegisterDto orderRegisterDto = new OrderRegisterDto("사이다", 11, orderedAt);

            // when
            PromotionApplyResult promotionApplyResult = orderServiceWithCustom.applyPromotion(orderRegisterDto);

            // then
            assertAll(() -> assertThat(promotionApplyResult.hasQuantityWithoutPromotion()).isTrue(),
                    () -> assertThat(promotionApplyResult.getQuantityWithoutPromotion()).isEqualTo(1));
        }

        @Test
        void 프로모션_조건에_따르면_추가적인_재고가_필요하더라도_재고가_부족한_경우_추가로_가져와야_하는_수량을_표시하지_않는다() {
            // given
            OrderRegisterDto orderRegisterDto = new OrderRegisterDto("사이다", 9, OrderServiceTest.this.orderedAt);

            // when
            PromotionApplyResult promotionApplyResult = orderServiceWithCustom.applyPromotion(orderRegisterDto);

            // then
            assertAll(() -> assertThat(promotionApplyResult.hasMissingOrderQuantity()).isFalse(),
                    () -> assertThat(promotionApplyResult.getMissingOrderQuantity()).isEqualTo(0));
        }
    }
}
