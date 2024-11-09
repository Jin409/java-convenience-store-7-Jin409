package store.service;


import static store.service.ErrorMessages.OrderService.INVALID_PRODUCT;

import store.dto.OrderRegisterDto;
import store.model.Product;
import store.model.promotion.Promotion;
import store.model.repository.ProductRepository;
import store.service.product.InventoryHandler;

public class OrderService {
    private final ProductRepository productRepository;
    private final InventoryHandler inventoryHandler;

    public OrderService(ProductRepository productRepository, InventoryHandler inventoryHandler) {
        this.productRepository = productRepository;
        this.inventoryHandler = inventoryHandler;
    }

    public void processOrder(OrderRegisterDto orderRegisterDto, boolean isMembershipDiscountApplied) {
        String nameOfProduct = orderRegisterDto.nameOfProduct();
        Product product = getProductWithName(nameOfProduct);
        inventoryHandler.reduceQuantity(product, orderRegisterDto.quantity(), orderRegisterDto.orderAt());
        if (isMembershipDiscountApplied) {
            applyMembershipDiscount(orderRegisterDto);
        }
    }

    private void applyMembershipDiscount(OrderRegisterDto orderRegisterDto) {

    }

    public PromotionApplyResult applyPromotion(OrderRegisterDto orderRegisterDto) {
        String nameOfProduct = orderRegisterDto.nameOfProduct();
        Product product = getProductWithName(nameOfProduct);
        if (product.hasPromotion() && product.isPromotionAvailable(orderRegisterDto.orderAt())) {
            return getApplyResult(product, orderRegisterDto);
        }
        return new PromotionApplyResult(product.getName(), 0, 0);
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
