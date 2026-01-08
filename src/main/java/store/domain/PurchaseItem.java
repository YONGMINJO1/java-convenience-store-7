package store.domain;

public class PurchaseItem {
    private final String name;
    private final int quantity;

    public PurchaseItem(String name, int quantity) {
        validateName(name);
        validateQuantity(quantity);

        this.name = name;
        this.quantity = quantity;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 상품이 비어있습니다.");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("[ERROR] 수량은 1개 이상이어야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
