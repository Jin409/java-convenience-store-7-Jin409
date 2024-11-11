package store.io.view.formatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import store.dto.ProductDisplayDto;
import store.dto.ProductDisplayDto.Stock;
import store.dto.ProductDisplayDto.Promotion;
import store.dto.ReceiptDto;
import store.dto.ReceiptDto.PaymentSummary;

public class OutputFormatter {

    private OutputFormatter() {
    }

    private static final String DECIMAL_FORMAT = "###,###";

    public static List<String> formatInformationOfProducts(List<ProductDisplayDto> productDisplayDtos) {
        List<String> results = new ArrayList<>();

        for (ProductDisplayDto productDisplayDto : productDisplayDtos) {
            StringJoiner stringJoiner = new StringJoiner(" ");

            stringJoiner.add("-");
            stringJoiner.add(productDisplayDto.getName());
            stringJoiner.add(new DecimalFormat(DECIMAL_FORMAT).format(productDisplayDto.getPrice()) + "원");

            if (productDisplayDto instanceof ProductDisplayDto.Stock) {
                String result = formatInformationOfDefaultProduct(stringJoiner, (Stock) productDisplayDto);
                results.add(result);
            }

            if (productDisplayDto instanceof ProductDisplayDto.Promotion) {
                String result = formatInformationOfPromotionProduct(stringJoiner, (Promotion) productDisplayDto);
                results.add(result);
            }
        }
        return results;
    }

    private static String formatInformationOfPromotionProduct(StringJoiner stringJoiner,
                                                              ProductDisplayDto.Promotion promotionProductDisplayDto) {
        formatQuantity(stringJoiner, promotionProductDisplayDto.getQuantityOfPromotion());
        stringJoiner.add(promotionProductDisplayDto.getNameOfPromotion());
        return stringJoiner.toString();
    }

    private static String formatInformationOfDefaultProduct(StringJoiner stringJoiner, Stock stockProductDisplayDto) {
        formatQuantity(stringJoiner, stockProductDisplayDto.getQuantity());
        return stringJoiner.toString();
    }

    private static void formatQuantity(StringJoiner stringJoiner, long quantity) {
        if (quantity == 0) {
            stringJoiner.add("재고 없음");
            return;
        }
        stringJoiner.add(quantity + "개");
    }

    public static String formatOrderedProducts(ReceiptDto.OrderedProduct orderedProduct) {
        return String.format("%-10s\t%-5d\t%-12s", orderedProduct.nameOfProduct(), orderedProduct.quantity(),
                String.format("%,d", orderedProduct.quantity() * orderedProduct.price()));
    }

    public static String formatFreeItem(ReceiptDto.FreeItem freeItem) {
        return String.format("%-10s\t%-5d", freeItem.nameOfProduct(), freeItem.quantity());
    }

    public static List<String> formatPaymentSummary(ReceiptDto receiptDto) {
        return List.of(formatTotalPurchase(receiptDto), formatPromotionDiscountResult(receiptDto),
                formatMembershipDiscountResult(receiptDto), formatAmountPay(receiptDto.getPaymentSummary()));
    }

    private static String formatTotalPurchase(ReceiptDto receiptDto) {
        return String.format("총구매액\t\t%-5d\t%-12s", receiptDto.countTotalOrderedQuantity(),
                String.format("%,d", receiptDto.getPaymentSummary().totalPrice()));
    }

    private static String formatPromotionDiscountResult(ReceiptDto receiptDto) {
        return String.format("행사할인\t\t%-5s",
                String.format("%,d", receiptDto.getPaymentSummary().promotionDiscountedPrice() * -1));
    }

    private static String formatMembershipDiscountResult(ReceiptDto receiptDto) {
        return String.format("멤버십할인\t%-10s",
                String.format("%,d", receiptDto.getPaymentSummary().membershipDiscountedPrice() * -1));
    }

    private static String formatAmountPay(PaymentSummary paymentSummary) {
        return String.format("내실돈\t\t%-12s", String.format("%,d",
                paymentSummary.totalPrice() - paymentSummary.promotionDiscountedPrice()
                        - paymentSummary.membershipDiscountedPrice()));
    }
}
