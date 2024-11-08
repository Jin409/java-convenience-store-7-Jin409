package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    private static List<Product> products;

    public ProductRepositoryImpl() {
        products = new ArrayList<>();
    }

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

}