package store.domain;

import java.time.LocalDate;

public class Promotion {
    // 필드 = Promotion이 가지고 있는 정보
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    // 생성자 = Promotion을 만드는 방법
    public Promotion(String name, int buy, int get,
                     LocalDate startDate, LocalDate endDate) {
        // 검증
        validateBuyAndGet(buy, get);
        validateDates(startDate, endDate);

        // 검증 이후 저장
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateBuyAndGet(int buy, int get) {
        if (buy <= 0 || get <= 0) {
            throw new IllegalArgumentException(
                    "[ERROR] 구매 수량과 증정 수량은 0보다 커야 합니다."
            );
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "[ERROR] 시작일이 종료일보다 늦을 수 없습니다."
            );
        }
    }

    public boolean isActiveOn(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public int calculateFreeQuantity(int purchaseQuantity) {
        int setSize = buy + get;
        return purchaseQuantity / setSize * get;
    }

    public int getPromotionSetSize() {
        return buy + get;
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

}
