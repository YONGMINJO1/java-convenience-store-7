package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    void 상품_생성_성공() {
        // given
        String name = "콜라";
        int price = 1000;
        int quantity = 10;

        // when
        Product product = new Product(name, price, quantity);

        // then
        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPrice()).isEqualTo(1000);
        assertThat(product.getQuantity()).isEqualTo(10);
    }

    @Test
    void 가격이_0이하면_예외() {
        // given
        String name = "콜라";
        int price = 0;
        int quantity = 10;

        // when & then
        assertThatThrownBy(() ->
                new Product(name, price, quantity)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 수량이_음수면_예외() {
        // when & then
        assertThatThrownBy(() ->
                new Product("콜라", 1000, -5)  // 음수!
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 상품명이_비어있으면_예외() {
        // when & then
        assertThatThrownBy(() ->
                new Product("", 1000, 10)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]")
        ;
    }

    @Test
    void 상품명이_null이면_예외() {
        // when & then
        assertThatThrownBy(() ->
                new Product(null, 1000, 10)
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 재고_차감_성공() {
        // given
        Product product = new Product("콜라", 1000, 10);

        // when
        product.decrease(3);

        // then
        assertThat(product.getQuantity()).isEqualTo(7);
    }

    @Test
    void 재고_부족시_예외() {
        // given
        Product product = new Product("콜라", 1000, 5);

        // when & then
        assertThatThrownBy(() -> product.decrease(10)
        ).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("재고 수량을 초과");
    }

    @Test
    void 음수_차감시_예외() {
        // given
        Product product = new Product("콜라", 1000, 10);

        // when & then
        assertThatThrownBy(() ->
                product.decrease(-3)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 재고_확인() {
        // given
        Product product = new Product("콜라", 1000, 5);

        // when & then
        assertThat(product.hasEnoughStock(3)).isTrue();
        assertThat(product.hasEnoughStock(5)).isTrue();
        assertThat(product.hasEnoughStock(6)).isFalse();
    }

    @Test
    void 가격_계산() {
        // given
        Product product = new Product("콜라", 1000, 10);

        // when & then
        assertThat(product.calculatePrice(1)).isEqualTo(1000);
        assertThat(product.calculatePrice(3)).isEqualTo(3000);
        assertThat(product.calculatePrice(10)).isEqualTo(10000);
    }

    @Test
    void 프로모션_상품_확인() {
        // given
        Promotion promotion = new Promotion(
                "탄산2+1", 2, 1,
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        Product withPromo = new Product("콜라", 1000, 10, promotion);
        Product withoutPromo = new Product("사이다", 1000, 10);

        // when & then
        assertThat(withPromo.hasPromotion()).isTrue();
        assertThat(withoutPromo.hasPromotion()).isFalse();
    }

    @Test
    void 품절_확인() {
        // given
        Product soldOut = new Product("콜라", 1000, 0);
        Product available = new Product("사이다", 1000, 5);

        // when & then
        assertThat(soldOut.isOutOfStock()).isTrue();
        assertThat(available.isOutOfStock()).isFalse();
    }
}
