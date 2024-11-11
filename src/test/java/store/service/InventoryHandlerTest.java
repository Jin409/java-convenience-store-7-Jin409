package store.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.config.InventoryHandlerConfig;
import store.model.ModelErrorMessages.OfStockItem;
import store.model.Product;
import store.model.StockItem;
import store.model.promotion.Promotion;
import store.model.promotion.PromotionItem;
import store.service.product.InventoryHandler;

public class InventoryHandlerTest {

    private InventoryHandler inventoryHandler;
    private LocalDate endAt;
    private Promotion promotion;

    @BeforeEach
    void setUp() {
        this.inventoryHandler = new InventoryHandlerConfig().inventoryHandler();
        this.endAt = LocalDate.of(2022, 10, 1);
        this.promotion = new Promotion("MD 추천", 1, 1, endAt.minusDays(2), endAt);
    }

    @Test
    void 할인이_적용되는_경우_할인_재고를_먼저_감소시킨다() {
        // given
        Product product = new Product("사이다", 10000, new StockItem(1), new PromotionItem(promotion, 1));

        // when
        inventoryHandler.reduceQuantity(product, 1, endAt.minusDays(1).atStartOfDay());

        // then
        assertAll(() -> assertThat(product.getPromotionItem().getPromotionQuantity()).isEqualTo(0),
                () -> assertThat(product.getStockItem().getQuantity()).isEqualTo(1));
    }

    @Test
    void 할인이_적용되지_않는_경우_기본_재고만_감소시킨다() {
        // given

        Product product = new Product("사이다", 10000, new StockItem(1), new PromotionItem(promotion, 1));

        // when
        inventoryHandler.reduceQuantity(product, 1, endAt.plusDays(1).atStartOfDay());

        // then
        assertAll(() -> assertThat(product.getPromotionItem().getPromotionQuantity()).isEqualTo(1),
                () -> assertThat(product.getStockItem().getQuantity()).isEqualTo(0));
    }

    @Test
    void 보유_수량보다_많은_수량을_주문하는_경우_예외를_발생시킨다() {
        // given

        Product product = new Product("사이다", 10000, new StockItem(1), new PromotionItem(promotion, 1));

        // when & then
        assertThatThrownBy(() -> inventoryHandler.reduceQuantity(product, 10000, endAt.atStartOfDay())).isInstanceOf(
                IllegalArgumentException.class).hasMessage(OfStockItem.NOT_ENOUGH_QUANTITY);
    }
}
