package store.domain;

import java.util.List;
import java.util.Map;

public class Receipt {
    private final List<PurchaseItem> purchaseItems;
    private final List<PurchaseItem> freeItems;
    private final int totalAmount;
    private final int promotionDiscount;
    private final int membershipDiscount;
    private final int finalAmount;
    private final Map<String, Integer> itemPrices;
    private final int totalQuantity;

    public Receipt(List<PurchaseItem> purchaseItems, List<PurchaseItem> freeItems, int totalAmount,
                   int promotionDiscount, int membershipDiscount, int finalAmount, Map<String, Integer> itemPrices,
                   int totalQuantity) {
        this.purchaseItems = purchaseItems;
        this.freeItems = freeItems;
        this.totalAmount = totalAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.finalAmount = finalAmount;
        this.itemPrices = itemPrices;
        this.totalQuantity = totalQuantity;
    }

    public List<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public List<PurchaseItem> getFreeItems() {
        return freeItems;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public int getItemPrice(String name) {

        return itemPrices.get(name);
    }

    public int getTotalQuantity() {
        int total = 0;
        for (PurchaseItem item : purchaseItems) {
            total += item.getQuantity();
        }
        return total;
    }


}
