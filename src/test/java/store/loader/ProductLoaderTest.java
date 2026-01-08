package store.loader;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;

public class ProductLoaderTest {

    @Test
    void 상품_파일_읽기() {
        // given
        PromotionLoader promotionLoader = new PromotionLoader();
        List<Promotion> promotions = promotionLoader.load();

        ProductLoader productLoader = new ProductLoader(promotions);

        // when
        List<Product> products = productLoader.load();

        // then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isGreaterThan(0);

    }

    @Test
    void 프로모션_없는_상품() {
        // given
        PromotionLoader promotionLoader = new PromotionLoader();
        List<Promotion> promotions = promotionLoader.load();

        ProductLoader productLoader = new ProductLoader(promotions);

        // when
        List<Product> products = productLoader.load();

        // then
        // 프로모션 없는 상품도 있는지 확인
        boolean hasNoPromotion = products.stream()
                .anyMatch(product -> !product.hasPromotion());

        assertThat(hasNoPromotion).isTrue();
    }
}
