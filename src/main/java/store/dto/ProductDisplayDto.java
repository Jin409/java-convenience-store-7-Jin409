package store.dto;

import store.model.Product;

public record ProductDisplayDto(String nameOfProduct, long price, long quantity, String nameOfPromotion) {
    public static ProductDisplayDto from(Product product) {

        if (product.getPromotion() == null) {
            return new ProductDisplayDto(product.getName(), product.getPrice(), product.getQuantity(), null);
        }
        return new ProductDisplayDto(product.getName(), product.getPrice(), product.getQuantity(),
                product.getPromotion().getName());
    }
}
