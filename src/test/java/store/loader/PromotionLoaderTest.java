package store.loader;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.domain.Promotion;

public class PromotionLoaderTest {

    @Test
    void 프로모션_파일_읽기() {
        // given
        PromotionLoader loader = new PromotionLoader();

        // when
        List<Promotion> promotions = loader.load();

        // then
        assertThat(promotions).hasSize(3);
    }
}
