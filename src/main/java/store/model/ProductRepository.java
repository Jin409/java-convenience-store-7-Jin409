package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

    private static List<Product> products;

    public ProductRepository() {
        products = new ArrayList<>();
    }

    public void save(Product product) {
        products.add(product);
    }

    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

}

