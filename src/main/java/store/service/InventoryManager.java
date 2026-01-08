package store.service;

import java.util.List;
import store.domain.Product;
import store.domain.PurchaseItem;

public class InventoryManager {
    private final List<Product> products;

    public InventoryManager(List<Product> products) {
        this.products = products;
    }

    // 상품 찾기
    public Product findProduct(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다.");
    }

    // 재고 확인
    public void validateStock(PurchaseItem item) {
        Product product = findProduct(item.getName());
        if (!product.hasEnoughStock(item.getQuantity())) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    // 재고 차감
    public void decreaseStock(PurchaseItem item) {
        Product product = findProduct(item.getName());
        product.decrease(item.getQuantity());
    }
}
