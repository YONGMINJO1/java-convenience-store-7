package store.parser;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import store.domain.PurchaseItem;

public class InputParserTest {

    @Test
    void 구매_항목_파싱() {
        // given
        InputParser parser = new InputParser();
        String input = "[콜라-3],[사이다-2]";

        // when
        List<PurchaseItem> items = parser.parsePurchaseItems(input);

        // then
        assertThat(items).hasSize(2);
        assertThat(items.get(0).getName()).isEqualTo("콜라");
        assertThat(items.get(0).getQuantity()).isEqualTo(3);
    }

    @Test
    void 공백이_있는_경우_파싱() {
        // given
        InputParser parser = new InputParser();
        String input = "[콜라-3], [사이다-2]";  // 공백 있음!

        // when
        List<PurchaseItem> items = parser.parsePurchaseItems(input);

        // then
        assertThat(items).hasSize(2);
        assertThat(items.get(0).getName()).isEqualTo("콜라");
        assertThat(items.get(1).getName()).isEqualTo("사이다");
    }

    @Test
    void 단일_항목_파싱() {
        // given
        InputParser parser = new InputParser();
        String input = "[콜라-10]";  // 1개만!

        // when
        List<PurchaseItem> items = parser.parsePurchaseItems(input);

        // then
        assertThat(items).hasSize(1);
        assertThat(items.get(0).getName()).isEqualTo("콜라");
        assertThat(items.get(0).getQuantity()).isEqualTo(10);
    }

    @Test
    void 큰_수량_파싱() {
        // given
        InputParser parser = new InputParser();
        String input = "[콜라-100]";

        // when
        List<PurchaseItem> items = parser.parsePurchaseItems(input);

        // then
        assertThat(items).hasSize(1);
        assertThat(items.get(0).getQuantity()).isEqualTo(100);
    }
}
