package store.service;


import static store.service.ErrorMessages.OrderService.INVALID_PRODUCT;

import java.util.List;
import store.dto.OrderRegisterDto;
import store.model.Order;
import store.model.Orders;
import store.model.Product;
import store.model.promotion.Promotion;
import store.model.repository.OrderRepository;
import store.model.repository.ProductRepository;
import store.service.product.InventoryHandler;

public class OrderService {
    private final ProductRepository productRepository;
    private final InventoryHandler inventoryHandler;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, InventoryHandler inventoryHandler,
                        OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.inventoryHandler = inventoryHandler;
        this.orderRepository = orderRepository;
    }

    public void processOrder(OrderRegisterDto orderRegisterDto) {
        String nameOfProduct = orderRegisterDto.nameOfProduct();
        Product product = getProductWithName(nameOfProduct);
        inventoryHandler.reduceQuantity(product, orderRegisterDto.quantity(), orderRegisterDto.orderAt());
        Order order = createOrder(product, orderRegisterDto);
        orderRepository.save(order);
    }

    private Order createOrder(Product product, OrderRegisterDto orderRegisterDto) {
        long quantity = orderRegisterDto.quantity();
        long discountedPrice = calculateDiscountedPrice(product, orderRegisterDto);
        return new Order(product, quantity, discountedPrice);
    }

    private long calculateDiscountedPrice(Product product, OrderRegisterDto orderRegisterDto) {
        long quantity = orderRegisterDto.quantity();
        if (product.isPromotionAvailable(orderRegisterDto.orderAt())) {
            Promotion promotion = product.getPromotionItem().getPromotion();
            return promotion.countFreeQuantity(quantity) * product.getPrice();
        }
        return 0;
    }

    public Orders createOrders() {
        List<Order> orders = orderRepository.findAll();
        return new Orders(orders, 0);
    }

    public PromotionApplyResult applyPromotion(OrderRegisterDto orderRegisterDto) {
        String nameOfProduct = orderRegisterDto.nameOfProduct();
        Product product = getProductWithName(nameOfProduct);
        if (product.hasPromotion() && product.isPromotionAvailable(orderRegisterDto.orderAt())) {
            return getApplyResult(product, orderRegisterDto);
        }
        return new PromotionApplyResult(product.getName(), 0, 0);
    }

    public void validateQuantity(List<OrderRegisterDto> orderRegisterDtos) {
        orderRegisterDtos.forEach(
                orderRegisterDto -> {
                    Product product = getProductWithName(orderRegisterDto.nameOfProduct());
                    inventoryHandler.hasEnoughQuantity(product, orderRegisterDto.quantity(),
                            orderRegisterDto.orderAt());
                }
        );
    }

    private PromotionApplyResult getApplyResult(Product product, OrderRegisterDto orderRegisterDto) {
        long orderedQuantity = orderRegisterDto.quantity();

        Promotion promotion = product.getPromotionItem().getPromotion();
        long quantityWithoutPromotion = product.getRemainingAfterApplyingPromotion(orderedQuantity);
        long missingQuantity = promotion.countQuantityToGet(orderedQuantity) - orderedQuantity;
        if (product.hasEnoughPromotionItems(orderedQuantity + missingQuantity)) {
            return new PromotionApplyResult(product.getName(), quantityWithoutPromotion, missingQuantity);
        }
        return new PromotionApplyResult(product.getName(), quantityWithoutPromotion, 0);
    }

    private Product getProductWithName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException(INVALID_PRODUCT));
    }
}
