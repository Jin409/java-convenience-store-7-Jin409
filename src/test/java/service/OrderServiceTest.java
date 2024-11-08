package service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import store.dto.OrderRegisterDto;
import store.model.Product;
import store.model.repository.OrderRepositoryImpl;
import store.model.repository.ProductRepository;
import store.service.ErrorMessages;
import store.service.OrderService;

public class OrderServiceTest {

    @Test
    void 존재하지_않는_이름의_상품을_주문하고자_하는_경우_예외가_발생한다() {
        // given
        ProductRepository fakeProductRepository = new ProductRepository() {
            @Override
            public void save(Product product) {
            }

            @Override
            public List<Product> findAll() {
                return List.of();
            }

            @Override
            public Optional<Product> findByName(String name) {
                return Optional.empty();
            }
        };

        OrderService orderService = new OrderService(new OrderRepositoryImpl(), fakeProductRepository);
        List<OrderRegisterDto> orderRegisterDtos = List.of(new OrderRegisterDto("사이다", 2L));

        // when & then
        assertThatThrownBy(() -> orderService.saveOrders(orderRegisterDtos)).isInstanceOf(
                IllegalArgumentException.class).hasMessage(ErrorMessages.OrderService.INVALID_PRODUCT);
    }
}
