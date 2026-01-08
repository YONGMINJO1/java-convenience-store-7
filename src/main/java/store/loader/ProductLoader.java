package store.loader;

import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.Promotion;
import store.util.FileLoader;

public class ProductLoader {

    private static final String FILE_PATH = "src/main/resources/products.md";
    private final List<Promotion> promotions;

    public ProductLoader(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    private Promotion findPromotionByName(String promotionName) {

        // "null" 이면 프로모션 없음
        if (promotionName.equals("null")) {
            return null;
        }
        // 프로모션 목록에서 찾기
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(promotionName)) {
                return promotion;
            }
        }
        // 못찾으면 null
        return null;
    }

    public List<Product> load() {
        // 1. 파일 읽기
        List<String> lines = FileLoader.loadLines(FILE_PATH);

        // 2. 헤더 제거
        lines.remove(0);

        // 3. 결과 리스트 준비
        List<Product> products = new ArrayList<>();

        // 4. 각 줄 처리
        for (String line : lines) {
            // 빈 줄 건너뛰기!
            if (line.trim().isEmpty()) {
                continue;
            }

            // 쉼표 나누기
            String[] parts = line.split(",");

            // 데이터 추출
            String name = parts[0].trim();
            int price = Integer.parseInt(parts[1].trim());
            int quantity = Integer.parseInt(parts[2].trim());
            String promotionName = parts[3].trim();

            // 프로모션 찾기
            Promotion promotion = findPromotionByName(promotionName);

            // Product 생성
            Product product = new Product(name, price, quantity, promotion);

            // 리스트에 추가
            products.add(product);
            // 확인
//            System.out.println("이름: " + name);
//            System.out.println("가격: " + price);
//            System.out.println("수량: " + quantity);
//            System.out.println("프로모션명: " + promotionName);
//            System.out.println("---");
            // 확인
//            System.out.println("처리중: " + line);
//            for (int i = 0; i < parts.length; i++) {
//                System.out.println(i + "; " + parts[i]);
//            }
//            System.out.println("---");
        }

        return products;
    }
}
