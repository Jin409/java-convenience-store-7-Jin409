package store.io.view;

import camp.nextstep.edu.missionutils.Console;
import store.service.PromotionApplyResult;

public class InputView {
    public static String getOrderFromCustomer() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    public static String askToAddMoreItems(PromotionApplyResult promotionApplyResult) {
        String stringBuilder = "현재 "
                + promotionApplyResult.getNameOfProduct()
                + "은(는) "
                + promotionApplyResult.getMissingOrderQuantity()
                + "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
        System.out.println(stringBuilder);
        return Console.readLine();
    }
}
