package store.controller;

import java.util.List;
import store.dto.ProductDisplayDto;
import store.dto.ProductRegisterDto;
import store.io.reader.ProductReader;
import store.io.view.OutputView;
import store.service.product.ProductService;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void saveProducts() {
        List<ProductRegisterDto> productRegisterDtos = ProductReader.readProducts();
        productService.saveProducts(productRegisterDtos);
    }

    public void displayInformation() {
        List<ProductDisplayDto> productDisplayDtos = productService.getAllProducts();
        OutputView.displayAllInformationOfProducts(productDisplayDtos);
    }
}
