package store.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.domain.Product;
import store.domain.PurchaseItem;
import store.domain.Receipt;

public class ReceiptGenerator {
    private final InventoryManager inventoryManager;
    private final PromotionCalculator promotionCalculator;

    public ReceiptGenerator(InventoryManager inventoryManager, PromotionCalculator promotionCalculator) {
        this.inventoryManager = inventoryManager;
        this.promotionCalculator = promotionCalculator;
    }

    public Receipt generate(List<PurchaseItem> purchaseItems, boolean useMembership) {
        int totalAmount = 0;
        int promotionDiscount = 0;
        int totalQuantity = 0;
        List<PurchaseItem> freeItems = new ArrayList<>();
        Map<String, Integer> itemPrices = new HashMap<>();

        // 각 구매 항목 처리
        for (PurchaseItem item : purchaseItems) {
            Product product = inventoryManager.findProduct(item.getName());

            int price = product.calculatePrice(item.getQuantity());

            totalAmount += price;
            totalQuantity += item.getQuantity();
            itemPrices.put(item.getName(), price);

            // 프로모션 적용
            if (promotionCalculator.canApplyPromotion(product)) {
                int freeQuantity = promotionCalculator.getFreeQuantity(product, item.getQuantity());
                if (freeQuantity > 0) {
                    freeItems.add(new PurchaseItem(item.getName(), freeQuantity));
                    promotionDiscount += promotionCalculator.getPromotionDiscount(product, freeQuantity);
                }
            }
        }
        // 멥버쉽 할인 (프로모션 미적용 금액의 30%, 최대 8000원)
        int membershipDiscount = 0;
        if (useMembership) {
            int nonPromotionAmount = totalAmount - promotionDiscount;
            membershipDiscount = Math.min((int) (nonPromotionAmount * 0.3), 8000);
        }

        int finalAmount = totalAmount - promotionDiscount - membershipDiscount;

        return new Receipt(purchaseItems, freeItems, totalAmount, promotionDiscount, membershipDiscount, finalAmount,
                itemPrices, totalQuantity);
    }
}
