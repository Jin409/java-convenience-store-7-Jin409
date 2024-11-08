package store.model;

import java.util.Optional;

public interface PromotionRepository {

    void save(Promotion promotion);

    Optional<Promotion> findByName(String name);
}
