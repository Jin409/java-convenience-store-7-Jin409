package store.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import store.dto.ProductRegisterDto;
import store.model.Promotion;
import store.model.repository.ProductRepositoryImpl;
import store.model.repository.PromotionRepository;
import store.service.product.ProductService;

public class ProductServiceTest {

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

        ProductService productService = new ProductService(new ProductRepositoryImpl(), promotionRepository);
        List<ProductRegisterDto> productRegisterDtos = List.of(
                new ProductRegisterDto("상품명", 1, 1, "존재하지 않는 프로모션", true));

        // when & then
        assertThatThrownBy(() -> productService.saveProducts(productRegisterDtos))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.ProductService.INVALID_PROMOTION_NAME);
    }
}
