package store.service;

import static store.service.ErrorMessages.ProductService.INVALID_PROMOTION_NAME;

import java.util.ArrayList;
import java.util.List;
import store.dto.ProductDisplayDto;
import store.dto.ProductRegisterDto;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionItem;
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
        for (ProductRegisterDto productRegisterDto : productRegisterDtos) {
            productRepository.save(createProduct(productRegisterDto));
        }
    }

    private Product createProduct(ProductRegisterDto productRegisterDto) {
        StockItem stockItem = null;
        PromotionItem promotionItem = null;

        if (productRegisterDto.hasPromotion()) {
            promotionItem = createPromotionItem(productRegisterDto);
        }
        if (!productRegisterDto.hasPromotion()) {
            stockItem = new StockItem(productRegisterDto.quantity());
        }
        return new Product(productRegisterDto.name(), productRegisterDto.price(), stockItem, promotionItem);
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
