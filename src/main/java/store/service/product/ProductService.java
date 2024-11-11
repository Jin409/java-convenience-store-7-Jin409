package store.service.product;

import static store.service.ErrorMessages.OrderService.INVALID_PRODUCT;
import static store.service.ErrorMessages.ProductService.INVALID_PROMOTION_NAME;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import store.dto.OrderRegisterDto;
import store.dto.ProductDisplayDto;
import store.dto.ProductRegisterDto;
import store.model.Product;
import store.model.promotion.Promotion;
import store.model.promotion.PromotionItem;
import store.model.StockItem;
import store.model.repository.ProductRepository;
import store.model.repository.PromotionRepository;

public class ProductService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final InventoryHandler inventoryHandler;

    public ProductService(ProductRepository productRepository, PromotionRepository promotionRepository,
                          InventoryHandler inventoryHandler) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
        this.inventoryHandler = inventoryHandler;
    }

    public void saveProducts(List<ProductRegisterDto> productRegisterDtos) {
        Map<String, List<ProductRegisterDto>> groupedDtos = groupByProductName(productRegisterDtos);
        groupedDtos.forEach((name, dtos) -> productRepository.save(createProduct(name, dtos)));
    }

    private Map<String, List<ProductRegisterDto>> groupByProductName(List<ProductRegisterDto> dtos) {
        return dtos.stream().collect(Collectors.groupingBy(
                ProductRegisterDto::name,
                LinkedHashMap::new,
                Collectors.toList()
        ));
    }

    private Product createProduct(String name, List<ProductRegisterDto> productRegisterDtos) {
        long price = productRegisterDtos.getFirst().price();
        PromotionItem promotionItem = findPromotionItem(productRegisterDtos);
        StockItem stockItem = createStockItem(productRegisterDtos);
        return new Product(name, price, stockItem, promotionItem);
    }

    private PromotionItem findPromotionItem(List<ProductRegisterDto> dtos) {
        for (ProductRegisterDto dto : dtos) {
            if (dto.isPromotion()) {
                return createPromotionItem(dto);
            }
        }
        return null;
    }

    private StockItem createStockItem(List<ProductRegisterDto> dtos) {
        for (ProductRegisterDto dto : dtos) {
            if (!dto.isPromotion()) {
                return new StockItem(dto.quantity());
            }
        }
        return new StockItem(0);
    }

    private PromotionItem createPromotionItem(ProductRegisterDto productRegisterDto) {
        Promotion promotion = promotionRepository.findByName(productRegisterDto.nameOfPromotion())
                .orElseThrow(() -> new IllegalArgumentException(INVALID_PROMOTION_NAME));
        return new PromotionItem(promotion, productRegisterDto.quantity());
    }

    public List<ProductDisplayDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDisplayDto> productDisplayDtos = new ArrayList<>();

        for (Product product : products) {
            if (product.hasPromotion()) {
                productDisplayDtos.add(createPromotionDisplayDto(product));
            }
            productDisplayDtos.add(createStockDisplayDto(product));
        }
        return productDisplayDtos;
    }

    private ProductDisplayDto.Promotion createPromotionDisplayDto(Product product) {
        return new ProductDisplayDto.Promotion(product.getName(), product.getPrice(),
                product.getPromotionItem().getPromotion().getName(), product.getPromotionItem().getPromotionQuantity());
    }

    private ProductDisplayDto.Stock createStockDisplayDto(Product product) {
        return new ProductDisplayDto.Stock(product.getName(), product.getPrice(), product.getStockItem().getQuantity());
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

    private Product getProductWithName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException(INVALID_PRODUCT));
    }
}
