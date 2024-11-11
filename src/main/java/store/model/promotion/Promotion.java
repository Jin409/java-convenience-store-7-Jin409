package store.model.promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final int quantityToBuy;
    private final int quantityToGet;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int quantityToBuy, int quantityToGet, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.quantityToBuy = quantityToBuy;
        this.quantityToGet = quantityToGet;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public boolean isApplicable(LocalDateTime orderedDate) {
        LocalDate orderedLocalDate = orderedDate.toLocalDate();
        return !startDate.isAfter(orderedLocalDate) && !endDate.isBefore(orderedLocalDate);
    }

    public long countApplicablePromotionQuantity(long orderedQuantity) {
        int promotionCycle = quantityToBuy + quantityToGet;
        return orderedQuantity / promotionCycle * promotionCycle;
    }

    public long countQuantityToGetInTotal(long orderedQuantity) {
        long quantityCondition = quantityToBuy + quantityToGet;
        long remainingQuantity = orderedQuantity - (orderedQuantity / quantityCondition * quantityCondition);

        if (remainingQuantity == quantityToBuy) {
            return orderedQuantity + 1;
        }
        return orderedQuantity;
    }

    public long countFreeQuantity(long orderedQuantity) {
        return orderedQuantity / (quantityToBuy + quantityToGet);
    }
}
