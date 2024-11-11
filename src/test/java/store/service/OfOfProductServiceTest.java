package store.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import store.config.AppConfig;
import store.config.InventoryHandlerConfig;
import store.config.RepositoryConfig;
import store.dto.ProductDisplayDto;
import store.dto.ProductRegisterDto;
import store.model.promotion.Promotion;
import store.model.repository.ProductRepositoryImpl;
import store.model.repository.PromotionRepository;
import store.service.ServiceErrorMessages.OfProductService;
import store.service.product.ProductService;

public class OfOfProductServiceTest {

    @Test
    void 존재하지_않은_프로모션에_적용되는_상품을_등록하고자_하면_예외가_발생한다() {
        // given
        PromotionRepository promotionRepository = new PromotionRepository() {
            @Override
            public void save(Promotion promotion) {
                return;
            }

            @Override
            public Optional<Promotion> findByName(String name) {
                return Optional.empty();
            }
        };

        ProductService productService = new ProductService(new ProductRepositoryImpl(), promotionRepository,
                new InventoryHandlerConfig().inventoryHandler());
        List<ProductRegisterDto> productRegisterDtos = List.of(
                new ProductRegisterDto("상품명", 1, 1, "존재하지 않는 프로모션", true));

        // when & then
        assertThatThrownBy(() -> productService.saveProducts(productRegisterDtos)).isInstanceOf(
                IllegalArgumentException.class).hasMessage(OfProductService.INVALID_PROMOTION_NAME);
    }

    @Test
    void 정상적으로_상품이_저장된다() {
        // given
        AppConfig appConfig = new AppConfig();
        PromotionRepository fakePromotionRepository = new PromotionRepository() {
            @Override
            public void save(Promotion promotion) {
                return;
            }

            @Override
            public Optional<Promotion> findByName(String name) {
                return Optional.of(
                        new Promotion("반짝 세일", 1, 1, LocalDate.of(2022, 10, 10), LocalDate.of(2022, 10, 10)));
            }
        };
        ProductService productService = new ProductService(
                new RepositoryConfig().productRepository(), fakePromotionRepository,
                new InventoryHandlerConfig().inventoryHandler()
        );
        List<ProductRegisterDto> productRegisterDtos = List.of(new ProductRegisterDto("사이다", 1000, 10, "반짝 세일", true),
                new ProductRegisterDto("사이다", 1000, 10, "null", false));

        // when
        productService.saveProducts(productRegisterDtos);

        // then
        List<ProductDisplayDto> allProducts = productService.getAllProducts();
        assertAll(
                () -> assertThat(allProducts.size()).isEqualTo(2),
                () -> assertThat(allProducts.getFirst())
                        .extracting("name", "price", "nameOfPromotion", "quantityOfPromotion")
                        .containsExactly("사이다", 1000L, "반짝 세일", 10L),
                () -> assertThat(allProducts.get(1))
                        .extracting("name", "price", "quantity")
                        .containsExactly("사이다", 1000L, 10L)
        );
    }
}
