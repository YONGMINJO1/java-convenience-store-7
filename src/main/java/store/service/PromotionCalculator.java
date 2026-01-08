package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.domain.Product;
import store.domain.Promotion;

public class PromotionCalculator {
    // 프로모션 적용 가능한지
    public boolean canApplyPromotion(Product product) {
        if (!product.hasPromotion()) {
            return false;
        }

        Promotion promotion = product.getPromotion();
        LocalDate today = DateTimes.now().toLocalDate();
        return promotion.isActiveOn(today);
    }

    // 1개 더 받을 수 있는지 (2+1에서 2개만 살때)
    public boolean canGetOneMore(Product product, int quantity) {
        if (!canApplyPromotion(product)) {
            return false;
        }

        Promotion promotion = product.getPromotion();
        int setSize = promotion.getPromotionSetSize(); //2+1 이면 3

        // 2+1에서 2개, 5개, 8개 .. 샀을때
        return quantity % setSize == promotion.getBuy();
    }

    // 프로모션 재고 부족 수량 (일부만 프로모션)
    public int getNonPromotionQuantity(Product product, int quantity) {
        if (!canApplyPromotion(product)) {
            return 0;
        }

        int promotionStock = product.getQuantity();
        if (quantity <= promotionStock) {
            return 0;
        }

        return quantity - promotionStock;
    }

    // 무료로 받는 수량
    public int getFreeQuantity(Product product, int quantity) {
        if (!canApplyPromotion(product)) {
            return 0;
        }

        Promotion promotion = product.getPromotion();
        return promotion.calculateFreeQuantity(quantity);
    }

    // 프로모션 할인 금액
    public int getPromotionDiscount(Product product, int freeQuantity) {
        return product.getPrice() * freeQuantity;
    }
}
