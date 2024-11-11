package store.controller;

import java.util.List;
import store.dto.OrderRegisterDto;
import store.handler.ErrorHandler;
import store.handler.InputHandler;
import store.service.OrderService;
import store.service.PromotionApplyResult;
import store.service.PromotionService;
import store.service.product.ProductService;

public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;
    private final PromotionService promotionService;

    public OrderController(OrderService orderService, ProductService productService,
                           PromotionService promotionService) {
        this.orderService = orderService;
        this.productService = productService;
        this.promotionService = promotionService;
    }

    public void processOrders() {
        List<OrderRegisterDto> orderFromCustomer = getOrders();
        for (OrderRegisterDto originDto : orderFromCustomer) {
            OrderRegisterDto validDto = getValidOrderRegisterDto(originDto);
            orderService.processOrder(validDto);
        }
    }

    private List<OrderRegisterDto> getOrders() {
        while (true) {
            try {
                List<OrderRegisterDto> orderFromCustomer = InputHandler.getOrderFromCustomer();
                productService.validateQuantity(orderFromCustomer);
                return orderFromCustomer;
            } catch (IllegalArgumentException e) {
                ErrorHandler.handle(e);
            }
        }
    }

    private OrderRegisterDto getValidOrderRegisterDto(OrderRegisterDto originDto) {
        PromotionApplyResult promotionApplyResult = promotionService.applyPromotion(originDto);
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

    private OrderRegisterDto createOrderWithOutPromotion(OrderRegisterDto originDto,
                                                         PromotionApplyResult promotionApplyResult) {
        return OrderRegisterDto.copyWithNewQuantity(originDto,
                originDto.quantity() - promotionApplyResult.getQuantityWithoutPromotion());
    }
}
