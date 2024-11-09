package store.service.product;

import static store.service.ErrorMessages.ProductService.INVALID_PROMOTION_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    public ProductService(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public void saveProducts(List<ProductRegisterDto> productRegisterDtos) {
        Map<String, List<ProductRegisterDto>> groupedDtos = groupByProductName(productRegisterDtos);
        groupedDtos.forEach((name, dtos) -> productRepository.save(createProduct(name, dtos)));
    }

    private Map<String, List<ProductRegisterDto>> groupByProductName(List<ProductRegisterDto> dtos) {
        return dtos.stream().collect(Collectors.groupingBy(ProductRegisterDto::name));
    }

    private Product createProduct(String name, List<ProductRegisterDto> productRegisterDtos) {
        long price = productRegisterDtos.getFirst().price();
        PromotionItem promotionItem = findPromotionItem(productRegisterDtos);
        StockItem stockItem = createStockItem(productRegisterDtos);
        return new Product(name, price, stockItem, promotionItem);
    }

    private PromotionItem findPromotionItem(List<ProductRegisterDto> dtos) {
        return dtos.stream().filter(ProductRegisterDto::isPromotion).findFirst().map(this::createPromotionItem)
                .orElse(null);
    }

    private StockItem createStockItem(List<ProductRegisterDto> dtos) {
        return dtos.stream().filter(dto -> !dto.isPromotion()).findFirst().map(dto -> new StockItem(dto.quantity()))
                .orElse(new StockItem(0));
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
                ProductDisplayDto.Promotion productDisplayDto = new ProductDisplayDto.Promotion(product.getName(),
                        product.getPrice(), product.getPromotionItem().getPromotion().getName(),
                        product.getPromotionItem().getPromotionQuantity());
                productDisplayDtos.add(productDisplayDto);
            }
            if (product.hasStock()) {
                ProductDisplayDto.Stock productDisplayDto = new ProductDisplayDto.Stock(product.getName(),
                        product.getPrice(), product.getStockItem().getQuantity());
                productDisplayDtos.add(productDisplayDto);
            }
        }

        return productDisplayDtos;
    }
}
