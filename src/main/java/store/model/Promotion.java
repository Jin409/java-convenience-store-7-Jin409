package store.model;

import java.time.LocalDate;

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
}
