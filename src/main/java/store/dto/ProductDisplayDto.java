package store.dto;

public class ProductDisplayDto {
    private final String name;
    private final long price;

    public ProductDisplayDto(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public static class Stock extends ProductDisplayDto {
        private final long quantity;

        public Stock(String name, long price, long quantity) {
            super(name, price);
            this.quantity = quantity;
        }

        public long getQuantity() {
            return quantity;
        }
    }

    public static class Promotion extends ProductDisplayDto {
        private final String nameOfPromotion;
        private final long quantityOfPromotion;

        public Promotion(String name, long price, String nameOfPromotion, long quantityOfPromotion) {
            super(name, price);
            this.nameOfPromotion = nameOfPromotion;
            this.quantityOfPromotion = quantityOfPromotion;
        }

        public String getNameOfPromotion() {
            return nameOfPromotion;
        }

        public long getQuantityOfPromotion() {
            return quantityOfPromotion;
        }
    }

}