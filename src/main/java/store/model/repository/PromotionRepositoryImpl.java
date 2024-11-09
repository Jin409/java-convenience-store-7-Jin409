package store.model.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import store.model.promotion.Promotion;

public class PromotionRepositoryImpl implements PromotionRepository {
    private static List<Promotion> promotions;

    public PromotionRepositoryImpl() {
        promotions = new ArrayList<>();
    }

    @Override
    public void save(Promotion promotion) {
        promotions.add(promotion);
    }

    @Override
    public Optional<Promotion> findByName(String name) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(name))
                .findAny();
    }
}