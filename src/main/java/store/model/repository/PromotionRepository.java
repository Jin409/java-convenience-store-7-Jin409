package store.model.repository;

import java.util.Optional;
import store.model.Promotion;

public interface PromotionRepository {

    void save(Promotion promotion);

    Optional<Promotion> findByName(String name);
}
