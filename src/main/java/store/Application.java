package store;

import java.util.List;
import store.controller.StoreController;
import store.domain.Product;
import store.domain.Promotion;
import store.loader.ProductLoader;
import store.loader.PromotionLoader;
import store.parser.InputParser;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // 1.필요한 것들 생성
        // 2.Controller 생성
        // 3.실행


        // 1. 프로모션 로드
        PromotionLoader promotionLoader = new PromotionLoader();
        List<Promotion> promotions = promotionLoader.load();

        // 2. 상품 로드
        ProductLoader productLoader = new ProductLoader(promotions);
        List<Product> products = productLoader.load();

        // 확인
//        System.out.println("프로모션 개수 : " + promotions.size());
//        System.out.println("상품 개수 : " + products.size());

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        InputParser inputParser = new InputParser();

        StoreController controller = new StoreController(
                inputView,
                outputView,
                inputParser,
                products
        );

        // 4. 실행!
        controller.run();
    }
}
