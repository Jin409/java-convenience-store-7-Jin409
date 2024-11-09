package store.io.view;

import camp.nextstep.edu.missionutils.Console;
import store.service.PromotionApplyResult;

public class InputView {
    public static String getOrderFromCustomer() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public static String askToAddMoreItems(PromotionApplyResult promotionApplyResult) {
        String informationText = "현재 " + promotionApplyResult.getNameOfProduct() + "은(는) "
                + promotionApplyResult.getMissingOrderQuantity() + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
        System.out.println(informationText);
        return Console.readLine();
    }

    public static String askToBuyWithoutPromotion(PromotionApplyResult applyResult) {
        String informationText =
                "현재 " + applyResult.getQuantityWithoutPromotion() + "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까?(Y/N)";
        System.out.println(informationText);
        return Console.readLine();
    }

    public static String askToApplyMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return Console.readLine();
    }
}
