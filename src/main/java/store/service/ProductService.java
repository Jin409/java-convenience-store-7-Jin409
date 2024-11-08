package store.service;

import java.util.List;
import store.dto.ProductDisplayDto;
import store.dto.ProductRegisterDto;
import store.model.Product;
import store.model.ProductRepository;
import store.model.Promotion;
import store.model.PromotionRepository;

public class ProductService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    public ProductService(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    public void saveProducts(List<ProductRegisterDto> productRegisterDtos) {
        for (ProductRegisterDto productRegisterDto : productRegisterDtos) {
            if (productRegisterDto.nameOfPromotion().equals("null")) {
                Product product = new Product(productRegisterDto.name(), productRegisterDto.price(),
                        productRegisterDto.quantity(), null);
                productRepository.save(product);
                continue;
            }

            Promotion promotion = promotionRepository.findByName(productRegisterDto.nameOfPromotion()).orElseThrow();
            Product product = new Product(productRegisterDto.name(), productRegisterDto.price(),
                    productRegisterDto.quantity(), promotion);
            productRepository.save(product);
        }
    }

    public List<ProductDisplayDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductDisplayDto::from).toList();
    }
}
