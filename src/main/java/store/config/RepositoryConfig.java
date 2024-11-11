package store.config;

import store.model.repository.OrderRepository;
import store.model.repository.OrderRepositoryImpl;
import store.model.repository.ProductRepository;
import store.model.repository.ProductRepositoryImpl;
import store.model.repository.PromotionRepository;
import store.model.repository.PromotionRepositoryImpl;
import store.model.repository.ReceiptRepository;
import store.model.repository.ReceiptRepositoryImpl;

public class RepositoryConfig {
    public PromotionRepository promotionRepository() {
        return new PromotionRepositoryImpl();
    }

    public ProductRepository productRepository() {
        return new ProductRepositoryImpl();
    }

    public OrderRepository orderRepository() {
        return new OrderRepositoryImpl();
    }

    public ReceiptRepository receiptRepository() {
        return new ReceiptRepositoryImpl();
    }
}
