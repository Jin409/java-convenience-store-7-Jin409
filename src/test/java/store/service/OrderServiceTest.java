package store.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.config.AppConfig;
import store.dto.OrderRegisterDto;
import store.model.Product;
import store.model.StockItem;
import store.model.promotion.Promotion;
import store.model.promotion.PromotionItem;
import store.model.promotion.PromotionType;
import store.model.repository.ProductRepository;

public class OrderServiceTest {

    private OrderService orderService;
    private LocalDate orderedAt;
    private AppConfig appConfig;

    @BeforeEach
    void setUp() {
        this.appConfig = new AppConfig();
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

    @Test
    void 프로모션이_적용되어_추가로_가져와야_하는_수량이_있는_경우_알맞은_형태를_반환한다() {
        // given
        LocalDate orderedAt = LocalDate.of(2022, 10, 22);
        Promotion promotion = new Promotion("2+1", 1, 1, orderedAt.minusDays(1), orderedAt.plusDays(1),
                PromotionType.N_PLUS_M);
        ProductRepository productRepository = new ProductRepository() {
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
                return Optional.of(new Product("사이다", 1000, new StockItem(1), new PromotionItem(promotion, 1)));
            }
        };
        OrderService orderServiceWithCustom = new OrderService(productRepository, appConfig.inventoryHandler());
        OrderRegisterDto orderRegisterDto = new OrderRegisterDto("사이다", 1, LocalDate.of(2022, 10, 22));

        // when
        PromotionApplyResult promotionApplyResult = orderServiceWithCustom.applyPromotion(orderRegisterDto);

        // then
        assertAll(() -> assertThat(promotionApplyResult.isInvalid()).isTrue(),
                () -> assertThat(promotionApplyResult.getMissingOrderQuantity()).isEqualTo(1));
    }
}
