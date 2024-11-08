package store.model.repository;

import java.util.List;
import java.util.Optional;
import store.model.Product;


public interface ProductRepository {

    void save(Product product);

    List<Product> findAll();

    Optional<Product> findByName(String name);
}

