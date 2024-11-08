package store.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PromotionRepository {
    private static List<Promotion> promotions;

    public PromotionRepository() {
        promotions = new ArrayList<>();
    }

    public void save(Promotion promotion) {
        promotions.add(promotion);
    }

    public Optional<Promotion> findByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(name))
                .findAny();
    }
}
