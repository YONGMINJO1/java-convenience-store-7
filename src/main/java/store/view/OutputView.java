package store.view;

import java.util.List;
import store.domain.Product;

public class OutputView {

    public void printWelcome() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println("");
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printProducts(List<Product> products) {
        for (Product product : products) {
            printProduct(product);
        }
        System.out.println();
    }

    private void printProduct(Product product) {
        String info = String.format("- %s %,d원 %s %s",
                product.getName(),
                product.getPrice(),
                getQuantityText(product),
                getPromotionText(product)
        );
        System.out.println(info);
    }

    private Object getQuantityText(Product product) {
        if (product.getQuantity() == 0) {
            return "재고 없음";
        }
        return product.getQuantity() + "개";
    }

    private Object getPromotionText(Product product) {
        if (product.hasPromotion()) {
            return product.getPromotion().getName();
        }
        return "";
    }
}
