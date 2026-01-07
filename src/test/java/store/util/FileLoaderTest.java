package store.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class FileLoaderTest {

    @Test
    void 파일_읽기_성공() {
        // given (준비)
        String filePath = "src/main/resources/products.md";

        // when (실행)
        List<String> lines = FileLoader.loadLines(filePath);

        // then (검증)
        assertThat(lines).isNotEmpty();
        System.out.println("읽은 줄 수: " + lines.size());
        System.out.println("첫 번째 줄: " + lines.get(0));
    }
}
