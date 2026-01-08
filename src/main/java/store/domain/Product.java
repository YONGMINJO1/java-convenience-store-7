package store.domain;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion promotion;

    public Product(String name, int price, int quantity, Promotion promotion) {

        // 검증
        validateName(name);
        validatePrice(price);
        validateQuantity(quantity);

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "[ERROR] 상품명은 비어있을 수 없습니다."
            );
        }
    }

    private void validatePrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException(
                    "[ERROR] 가격은 0보다 커야 합니다."
            );
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "[ERROR] 수량은 0 이상이어야 합니다."
            );
        }
    }

    public Product(String name, int price, int quantity) {
        this(name, price, quantity, null);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    // 기능 - 프로모션 있는지 확인
    public boolean hasPromotion() {
        return promotion != null;
    }

    // 기능 - 재고가 충분한지 확인
    public boolean hasEnoughStock(int required) {
        return quantity >= required;
    }

    // 기능 - 재고 차감하기
    public void decrease(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException(
                    "[ERROR] 재고 수량을 초과할 수 없습니다."
            );
        }
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "[ERROR] 차감 수량은 0 이상이어야 합니다."
            );
        }
        quantity -= amount;
    }

    // 기능 - 가격 계산하기
    public int calculatePrice(int purchaseQuantity) {
        return price * purchaseQuantity;
    }

    // 기능 - 품절인지 확인
    public boolean isOutOfStock() {
        return quantity == 0;
    }
}
