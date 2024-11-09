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
import store.service.PromotionApplyResult;
import store.service.PromotionService;
import store.service.product.ProductService;

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
        for (OrderRegisterDto originDto : orderFromCustomer) {
            OrderRegisterDto validDto = getValidOrderRegisterDto(originDto);
            orderService.processOrder(validDto);
        }
    }

    private OrderRegisterDto getValidOrderRegisterDto(OrderRegisterDto originDto) {
        PromotionApplyResult promotionApplyResult = orderService.applyPromotion(originDto);
        OrderRegisterDto validDto = originDto;

        if (promotionApplyResult.hasQuantityWithoutPromotion()) {
            return handleInsufficientQuantity(originDto, promotionApplyResult);
        }

        if (promotionApplyResult.hasMissingOrderQuantity()) {
            validDto = handleAdditionalItems(originDto, promotionApplyResult);
        }
        return validDto;
    }

    private OrderRegisterDto handleInsufficientQuantity(OrderRegisterDto originDto,
                                                        PromotionApplyResult promotionApplyResult) {
        AnswerSign answerSign = InputHandler.askToBuyWithoutPromotion(promotionApplyResult);
        if (answerSign.meansFalse()) {
            return createOrderWithOutPromotion(originDto, promotionApplyResult);
        }
        return originDto;
    }

    private OrderRegisterDto createOrderWithOutPromotion(OrderRegisterDto originDto,
                                                         PromotionApplyResult promotionApplyResult) {
        return OrderRegisterDto.copyWithNewQuantity(originDto,
                originDto.quantity() - promotionApplyResult.getQuantityWithoutPromotion());
    }

    private OrderRegisterDto handleAdditionalItems(OrderRegisterDto originDto,
                                                   PromotionApplyResult promotionApplyResult) {
        AnswerSign answerSign = InputHandler.askToAddMoreItems(promotionApplyResult);
        if (answerSign.meansTrue()) {
            return createOrderWithAdditionalQuantity(originDto, promotionApplyResult);
        }
        return originDto;
    }

    private OrderRegisterDto createOrderWithAdditionalQuantity(OrderRegisterDto originDto,
                                                               PromotionApplyResult promotionApplyResult) {
        return OrderRegisterDto.copyWithNewQuantity(originDto,
                originDto.quantity() + promotionApplyResult.getMissingOrderQuantity());
    }
}
