package store.model.promotion;

import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int quantityToBuy;
    private final int quantityToGet;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final PromotionType promotionType;

    public Promotion(String name, int quantityToBuy, int quantityToGet, LocalDate startDate, LocalDate endDate,
                     PromotionType promotionType) {
        this.name = name;
        this.quantityToBuy = quantityToBuy;
        this.quantityToGet = quantityToGet;
        this.startDate = startDate;
        this.endDate = endDate;
        this.promotionType = promotionType;
    }

    public String getName() {
        return name;
    }

    public boolean isApplicable(LocalDate orderedDate) {
        return startDate.isBefore(orderedDate) && endDate.isAfter(orderedDate);
    }
}
