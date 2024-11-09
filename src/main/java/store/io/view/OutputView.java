package store.io.view;

import java.util.List;
import store.dto.ProductDisplayDto;
import store.dto.ReceiptDto;
import store.io.view.formatter.OutputFormatter;

public class OutputView {

    public static void displayAllInformationOfProducts(List<ProductDisplayDto> productDisplayDtos) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");

        List<String> information = OutputFormatter.formatInformationOfProducts(productDisplayDtos);
        information.forEach(System.out::println);
    }

    public static void displayReceipt(ReceiptDto receiptDto) {
        System.out.println("==============W 편의점================");

        System.out.println("상품명\t\t수량\t금액");
        for (ReceiptDto.OrderedProduct orderedProduct : receiptDto.getOrderedProducts()) {
            System.out.printf("%-10s\t%-5d\t%-12s\n", orderedProduct.nameOfProduct(), orderedProduct.quantity(),
                    String.format("%,d", orderedProduct.price()));
        }

        System.out.println("=============증정=============");
        for (ReceiptDto.FreeItem freeItem : receiptDto.getFreeItems()) {
            System.out.printf("%-10s\t%-5d\n", freeItem.nameOfProduct(), freeItem.quantity());
        }

        System.out.println("====================================");
        ReceiptDto.PaymentSummary paymentSummary = receiptDto.getPaymentSummary();

        long amountToPay = paymentSummary.totalPrice() - paymentSummary.promotionDiscountedPrice()
                - paymentSummary.membershipDiscountedPrice();

        System.out.printf("총구매액\t\t%-5d\t%-12s\n", receiptDto.getOrderedProducts().size(),
                String.format("%,d", paymentSummary.totalPrice()));

        System.out.printf("행사할인\t\t%-5d\n", paymentSummary.promotionDiscountedPrice());

        System.out.printf("멤버십할인\t%-10d\n", paymentSummary.membershipDiscountedPrice());

        System.out.printf("내실돈\t\t%-12s\n", String.format("%,d", amountToPay));
    }

    public static void displayErrorMessage(String message) {
        System.out.println(message);
    }
}
