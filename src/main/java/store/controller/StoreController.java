package store.controller;


import java.util.List;
import store.dto.OrderRegisterDto;
import store.dto.ProductDisplayDto;
import store.dto.ProductRegisterDto;
import store.dto.PromotionRegisterDto;
import store.handler.InputHandler;
import store.io.ProductReader;
import store.io.PromotionReader;
import store.io.view.OutputView;
import store.model.Orders;
import store.service.MembershipDiscountService;
import store.service.OrderService;
import store.service.PromotionApplyResult;
import store.service.PromotionService;
import store.service.ReceiptService;
import store.service.product.ProductService;

public class StoreController {
    private final PromotionService promotionService;
    private final ProductService productService;
    private final OrderService orderService;
    private final MembershipDiscountService membershipDiscountService;
    private final ReceiptService receiptService;

    public StoreController(PromotionService promotionService, ProductService productService, OrderService orderService,
                           MembershipDiscountService membershipDiscountService, ReceiptService receiptService) {
        this.promotionService = promotionService;
        this.productService = productService;
        this.orderService = orderService;
        this.membershipDiscountService = membershipDiscountService;
        this.receiptService = receiptService;
    }

    public void run() {
        savePromotions();
        saveProducts();

        do {
            displayInformation();
            processOrders();
        } while (InputHandler.askToLeave().meansTrue());
    }

    private void savePromotions() {
        List<PromotionRegisterDto> promotionRegisterDtos = PromotionReader.readPromotions();
        promotionService.savePromotions(promotionRegisterDtos);
    }

    private void saveProducts() {
        List<ProductRegisterDto> productRegisterDtos = ProductReader.readProducts();
        productService.saveProducts(productRegisterDtos);
    }

    private void displayInformation() {
        List<ProductDisplayDto> productDisplayDtos = productService.getAllProducts();
        OutputView.displayAllInformationOfProducts(productDisplayDtos);
    }

    private void processOrders() {
        List<OrderRegisterDto> orderFromCustomer = getOrders();
        for (OrderRegisterDto originDto : orderFromCustomer) {
            OrderRegisterDto validDto = getValidOrderRegisterDto(originDto);
            orderService.processOrder(validDto);
        }
        Orders orders = getOrdersWithMembershipDiscount();
        OutputView.displayReceipt(receiptService.getReceipt(orders));
    }

    private Orders getOrdersWithMembershipDiscount() {
        AnswerSign answerSign = InputHandler.askToApplyMembershipDiscount();
        if (answerSign.meansTrue()) {
            return membershipDiscountService.getOrdersWithMembershipDiscount();
        }
        return orderService.createOrders();
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
