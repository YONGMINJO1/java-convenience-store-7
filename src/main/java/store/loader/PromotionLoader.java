package store.loader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import store.domain.Promotion;
import store.util.FileLoader;

public class PromotionLoader {

    private static final String FILE_PATH = "src/main/resources/promotions.md";

    public List<Promotion> load() {
        List<String> lines = FileLoader.loadLines(FILE_PATH);

        // 2단계
        lines.remove(0);

        List<Promotion> promotions = new ArrayList<>();

        // 확인
        //System.out.println("헤더 제거 후:");
        for (String line : lines) {
            // 4단계
            String[] parts = line.split(",");

            String name = parts[0];
            int buy = Integer.parseInt(parts[1]);
            int get = Integer.parseInt(parts[2]);
            LocalDate startDate = LocalDate.parse(parts[3]);
            LocalDate endDate = LocalDate.parse(parts[4]);

            Promotion promotion = new Promotion(name, buy, get, startDate, endDate);

            promotions.add(promotion);
        }
        return promotions;
    }

}
