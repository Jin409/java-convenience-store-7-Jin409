package store.controller;

import java.util.List;
import store.dto.ProductDisplayDto;
import store.dto.ProductRegisterDto;
import store.dto.PromotionRegisterDto;
import store.io.MarkDownReader;
import store.io.view.OutputView;
import store.service.ProductService;
import store.service.PromotionService;

public class StoreController {

    private final PromotionService promotionService;
    private final ProductService productService;

    public StoreController(PromotionService promotionService, ProductService productService) {
        this.promotionService = promotionService;
        this.productService = productService;
    }

    public void run() {
        savePromotions();
        saveProducts();
        displayInformation();
    }

    private void displayInformation() {
        List<ProductDisplayDto> productDisplayDtos = productService.getAllProducts();
        OutputView.displayAllInformationOfProducts(productDisplayDtos);
    }

    private void savePromotions() {
        List<PromotionRegisterDto> promotionRegisterDtos = MarkDownReader.readPromotions();
        promotionService.savePromotions(promotionRegisterDtos);
    }

    private void saveProducts() {
        List<ProductRegisterDto> productRegisterDtos = MarkDownReader.readProducts();
        productService.saveProducts(productRegisterDtos);
    }

}
