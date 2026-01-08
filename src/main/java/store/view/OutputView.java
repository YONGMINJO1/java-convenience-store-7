package store.view;

import java.util.List;
import store.domain.Product;
import store.domain.PurchaseItem;
import store.domain.Receipt;

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
        String info = String.format("- %s %,d원 %s %s", product.getName(), product.getPrice(), getQuantityText(product),
                getPromotionText(product));
        System.out.println(info);
    }

    private String getQuantityText(Product product) {
        if (product.getQuantity() == 0) {
            return "재고 없음";
        }
        return product.getQuantity() + "개";
    }

    private String getPromotionText(Product product) {
        if (product.hasPromotion()) {
            return product.getPromotion().getName();
        }
        return "";
    }

    public void printReceipt(Receipt receipt) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");

        // 구매 상품
        for (PurchaseItem item : receipt.getPurchaseItems()) {

            //Product product = inventoryManager.findProduct(item.getName());
            int price = receipt.getItemPrice(item.getName());

            System.out.printf("%s\t\t%d\t%,d\n", item.getName(), item.getQuantity(), price);
        }

        // 증정
        System.out.println("=============증정===============");
        for (PurchaseItem item : receipt.getFreeItems()) {
            System.out.printf("%s\t\t%d\n", item.getName(), item.getQuantity());
        }

        // 금액
        System.out.println("====================================");
        System.out.printf("총구매액\t\t%d\t%,d\n", receipt.getTotalQuantity(), receipt.getTotalAmount());
        System.out.printf("행사할인\t\t\t-%,d\n", receipt.getPromotionDiscount());
        System.out.printf("멤버십할인\t\t\t-%,d\n", receipt.getMembershipDiscount());
        System.out.printf("내실돈\t\t\t%,d\n", receipt.getFinalAmount());
    }
}
