package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class PromotionTest {

    @Test
    void 프로모션_생성_성공() {
        // given
        String name = "탄산2+1";
        int buy = 2;
        int get = 1;
        LocalDate startDate = LocalDate.of(2014, 1, 1);
        LocalDate endDate = LocalDate.of(2014, 12, 31);

        // when
        Promotion promotion = new Promotion(name, buy, get, startDate, endDate);

        // then
        assertThat(promotion.getName()).isEqualTo("탄산2+1");
        assertThat(promotion.getBuy()).isEqualTo(2);
        assertThat(promotion.getGet()).isEqualTo(1);
    }

    @Test
    void 구매수량이_음수면_예외() {
        // given
        String name = "탄산2+1";
        int buy = -2;  // 잘못된 값!
        int get = 1;
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        // when & then
        assertThatThrownBy(() ->
                new Promotion(name, buy, get, startDate, endDate)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 증정수량이_0이하면_예외() {
        // when & then
        assertThatThrownBy(() ->
                new Promotion("탄산2+1", 2, 0,
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 12, 31))
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 시작일이_종료일보다_늦으면_예외() {
        // when & then
        assertThatThrownBy(() ->
                new Promotion("탄산2+1", 2, 1,
                        LocalDate.of(2024, 12, 31),  // 종료일
                        LocalDate.of(2024, 1, 1))    // 시작일 (순서가 바뀜!)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 프로모션_기간_확인() {
        // given
        Promotion promotion = new Promotion(
                "탄산2+1", 2, 1,
                LocalDate.of(2024, 1, 1),   // 시작
                LocalDate.of(2024, 1, 31)   // 종료
        );

        // when & then
        // 기간 전
        assertThat(promotion.isActiveOn(LocalDate.of(2023, 12, 31)))
                .isFalse();

        // 기간 중
        assertThat(promotion.isActiveOn(LocalDate.of(2024, 1, 15)))
                .isTrue();

        // 기간 후
        assertThat(promotion.isActiveOn(LocalDate.of(2024, 2, 1)))
                .isFalse();
    }

    @Test
    void 공짜수량_계산() {
        // given
        Promotion promotion = new Promotion(
                "탄산2+1", 2, 1,
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        // when & then
        assertThat(promotion.calculateFreeQuantity(2)).isEqualTo(0);  // 1세트 미만
        assertThat(promotion.calculateFreeQuantity(3)).isEqualTo(1);  // 정확히 1세트
        assertThat(promotion.calculateFreeQuantity(7)).isEqualTo(2);  // 2세트 + 1개
        assertThat(promotion.calculateFreeQuantity(10)).isEqualTo(3); // 3세트 + 1개
    }
}
