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

    public void processOrder(OrderRegisterDto orderRegisterDto) {
        String nameOfProduct = orderRegisterDto.nameOfProduct();
        Product product = getProductWithName(nameOfProduct);
        inventoryHandler.reduceQuantity(product, orderRegisterDto.quantity(), orderRegisterDto.orderAt());
    }

    public PromotionApplyResult applyPromotion(OrderRegisterDto orderRegisterDto) {
        String nameOfProduct = orderRegisterDto.nameOfProduct();
        Product product = getProductWithName(nameOfProduct);

        if (product.hasPromotion()) {
            return getApplyResult(product, orderRegisterDto);
        }
        return new PromotionApplyResult(false, product.getName(), 0);
    }

    private PromotionApplyResult getApplyResult(Product product, OrderRegisterDto orderRegisterDto) {
        Promotion promotion = product.getPromotionItem().getPromotion();
        long missingQuantity = promotion.countQuantityToGet(orderRegisterDto.quantity()) - orderRegisterDto.quantity();
        if (missingQuantity > 0) {
            return new PromotionApplyResult(true, product.getName(), missingQuantity);
        }
        return new PromotionApplyResult(false, product.getName(), missingQuantity);
    }

    private Product getProductWithName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException(INVALID_PRODUCT));
    }
}
