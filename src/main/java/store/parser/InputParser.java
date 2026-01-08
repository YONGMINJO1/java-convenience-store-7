package store.parser;

import java.util.ArrayList;
import java.util.List;
import store.domain.PurchaseItem;

public class InputParser {

    public List<PurchaseItem> parsePurchaseItems(String input) {
        // 여기에 파싱 로직 구현 예정
        // "[콜라-3], [사이다-2]"

        // 1단계 쉼표로 나누기
        String[] itemStrings = input.split(",");

        // 결과를 담을 리스트
        List<PurchaseItem> items = new ArrayList<>();

        // 확인
        for (String itemString : itemStrings) {
            // 여기서 하나씩 파싱
            PurchaseItem item = parseItem(itemString);
            items.add(item);
        }
        return items; // null 말고 items 반환!
    }

    private PurchaseItem parseItem(String itemString) {
        itemString = itemString.trim();
        // 검증
        if (itemString == null || itemString.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        if (itemString.length() < 3) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }

        if (!itemString.startsWith("[") || !itemString.endsWith("]")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
        // "[콜라-3]"
        // 앞뒤 공백 제거
        // 대괄호 제거
        // "[콜라-3]" -> "콜라-3"
        String content = itemString.substring(1, itemString.length() - 1);
        //System.out.println("대괄호 제거 : " + content);

        // "콜라-3" -> ["콜라", "3"]
        String[] parts = content.split("-");

//        System.out.println("이름: " + parts[0]);
//        System.out.println("수량: " + parts[1]);
        // 데이터 추출
        String name = parts[0].trim();
        int quantity = Integer.parseInt(parts[1].trim());

        // 객체 생성
        return new PurchaseItem(name, quantity);
    }
}
