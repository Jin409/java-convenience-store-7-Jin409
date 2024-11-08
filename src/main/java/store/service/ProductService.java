package store.service;

import java.util.ArrayList;
import java.util.List;
import store.dto.ProductDisplayDto;
import store.dto.ProductRegisterDto;
import store.model.DefaultProduct;
import store.model.Product;
import store.model.Promotion;
import store.model.PromotionProduct;
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
            if (productRegisterDto.nameOfPromotion().equals("null")) {
                Product product = new store.model.DefaultProduct(productRegisterDto.name(), productRegisterDto.price(),
                        productRegisterDto.price());
                productRepository.save(product);
                continue;
            }

            Promotion promotion = promotionRepository.findByName(productRegisterDto.nameOfPromotion()).orElseThrow();
            Product product = new PromotionProduct(productRegisterDto.name(), productRegisterDto.price(), promotion,
                    productRegisterDto.quantity());
            productRepository.save(product);
        }
    }

    public List<ProductDisplayDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDisplayDto> productDisplayDtos = new ArrayList<>();

        for (Product product : products) {
            if (product instanceof DefaultProduct defaultProduct) {
                ProductDisplayDto displayDto = new ProductDisplayDto.Default(product.getName(), product.getPrice(),
                        defaultProduct.getQuantity());
                productDisplayDtos.add(displayDto);
            }

            if (product instanceof PromotionProduct promotionProduct) {
                ProductDisplayDto displayDto = new ProductDisplayDto.Promotion(product.getName(), product.getPrice(),
                        promotionProduct.getPromotion().getName(), promotionProduct.getPromotionQuantity());
                productDisplayDtos.add(displayDto);
            }

        }

        return productDisplayDtos;
    }
}
