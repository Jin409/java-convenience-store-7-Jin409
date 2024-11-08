package store.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.params.ParameterizedTest;

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