package store.controller;

import java.util.List;
import store.dto.OrderRegisterDto;
import store.handler.InputHandler;
import store.io.view.OutputView;
import store.model.Orders;
import store.service.MembershipDiscountService;
import store.service.OrderService;
import store.service.PromotionApplyResult;

public class OrderController {
    private final OrderService orderService;
    private final MembershipDiscountService membershipDiscountService;

    public OrderController(OrderService orderService, MembershipDiscountService membershipDiscountService) {
        this.orderService = orderService;
        this.membershipDiscountService = membershipDiscountService;
    }

    public void processOrders() {
        List<OrderRegisterDto> orderFromCustomer = getOrders();
        for (OrderRegisterDto originDto : orderFromCustomer) {
            OrderRegisterDto validDto = getValidOrderRegisterDto(originDto);
            orderService.processOrder(validDto);
        }
    }

    public Orders getProcessedOrders() {
        AnswerSign answerSign = InputHandler.askToApplyMembershipDiscount();
        if (answerSign.meansTrue()) {
            return membershipDiscountService.getOrdersWithMembershipDiscount();
        }
        return orderService.createOrders();
    }

    private List<OrderRegisterDto> getOrders() {
        while (true) {
            try {
                List<OrderRegisterDto> orderFromCustomer = InputHandler.getOrderFromCustomer();
                orderService.validateQuantity(orderFromCustomer);
                return orderFromCustomer;
            } catch (IllegalArgumentException e) {
                OutputView.displayErrorMessage(e.getMessage());
            }
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
