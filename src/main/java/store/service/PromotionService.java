package store.service;

import static store.service.ServiceErrorMessages.OfOrderService.INVALID_PRODUCT;

import java.util.List;
import store.dto.OrderRegisterDto;
import store.dto.PromotionApplyResult;
import store.dto.PromotionRegisterDto;
import store.model.Product;
import store.model.promotion.Promotion;
import store.model.promotion.PromotionItem;
import store.model.repository.ProductRepository;
import store.model.repository.PromotionRepository;

public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    public PromotionService(PromotionRepository promotionRepository, ProductRepository productRepository) {
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
    }

    public void savePromotions(List<PromotionRegisterDto> promotionRegisterDtos) {
        List<Promotion> promotions = promotionRegisterDtos.stream()
                .map(dto -> new Promotion(dto.name(), dto.quantityToBuy(), dto.quantityToGet(), dto.startDate(),
                        dto.endDate())).toList();
        promotions.forEach(promotionRepository::save);
    }

    public PromotionApplyResult applyPromotion(OrderRegisterDto orderRegisterDto) {
        String nameOfProduct = orderRegisterDto.nameOfProduct();
        Product product = getProductWithName(nameOfProduct);
        if (product.hasPromotion() && product.isPromotionAvailable(orderRegisterDto.orderAt())) {
            return getPromotionAppliedResult(product, orderRegisterDto);
        }
        return new PromotionApplyResult(product.getName(), 0, 0);
    }

    private Product getProductWithName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException(INVALID_PRODUCT));
    }

    private PromotionApplyResult getPromotionAppliedResult(Product product, OrderRegisterDto orderRegisterDto) {
        long orderedQuantity = orderRegisterDto.quantity();
        PromotionItem promotionItem = product.getPromotionItem();

        long quantityWithoutPromotion = orderedQuantity - promotionItem.countPromotionalQuantityInInventory();
        long missingQuantity = promotionItem.countPromotionalQuantityAccordingTo(orderedQuantity) - orderedQuantity;

        if (product.hasEnoughPromotionQuantityFor(orderedQuantity + missingQuantity)) {
            return new PromotionApplyResult(product.getName(), quantityWithoutPromotion, missingQuantity);
        }
        return new PromotionApplyResult(product.getName(), quantityWithoutPromotion, 0);
    }
}
