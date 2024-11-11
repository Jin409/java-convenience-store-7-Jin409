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
        printHeader();
        printOrderedProducts(receiptDto);
        printFreeItems(receiptDto);
        printPaymentSummary(receiptDto);
    }

    private static void printHeader() {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
    }

    private static void printOrderedProducts(ReceiptDto receiptDto) {
        for (ReceiptDto.OrderedProduct orderedProduct : receiptDto.getOrderedProducts()) {
            System.out.println(OutputFormatter.formatOrderedProducts(orderedProduct));
        }
    }

    private static void printFreeItems(ReceiptDto receiptDto) {
        System.out.println("=============증정=============");
        for (ReceiptDto.FreeItem freeItem : receiptDto.getFreeItems()) {
            System.out.println(OutputFormatter.formatFreeItem(freeItem));
        }
    }

    private static void printPaymentSummary(ReceiptDto receiptDto) {
        System.out.println("====================================");
        OutputFormatter.formatPaymentSummary(receiptDto).forEach(System.out::println);
    }
}
