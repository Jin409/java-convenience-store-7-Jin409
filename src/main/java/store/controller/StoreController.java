package store.controller;


import java.util.List;
import store.dto.OrderRegisterDto;
import store.dto.ProductDisplayDto;
import store.dto.ProductRegisterDto;
import store.dto.PromotionRegisterDto;
import store.handler.InputHandler;
import store.io.MarkDownReader;
import store.io.view.OutputView;
import store.service.OrderService;
import store.service.ProductService;
import store.service.PromotionService;

public class StoreController {
    private final PromotionService promotionService;
    private final ProductService productService;
    private final OrderService orderService;

    public StoreController(PromotionService promotionService, ProductService productService,
                           OrderService orderService) {
        this.promotionService = promotionService;
        this.productService = productService;
        this.orderService = orderService;
    }

    public void run() {
        savePromotions();
        saveProducts();
        displayInformation();

        saveOrders();
    }

    private void savePromotions() {
        List<PromotionRegisterDto> promotionRegisterDtos = MarkDownReader.readPromotions();
        promotionService.savePromotions(promotionRegisterDtos);
    }

    private void saveProducts() {
        List<ProductRegisterDto> productRegisterDtos = MarkDownReader.readProducts();
        productService.saveProducts(productRegisterDtos);
    }

    private void displayInformation() {
        List<ProductDisplayDto> productDisplayDtos = productService.getAllProducts();
        OutputView.displayAllInformationOfProducts(productDisplayDtos);
    }

    private void saveOrders() {
        List<OrderRegisterDto> orderFromCustomer = InputHandler.getOrderFromCustomer();
        orderService.saveOrders(orderFromCustomer);
    }
}
