package store.controller;

import java.util.List;
import store.domain.Product;
import store.domain.PurchaseItem;
import store.parser.InputParser;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final InputParser inputParser;
    private final List<Product> products;

    public StoreController(InputView inputView,
                           OutputView outputView,
                           InputParser inputParser,
                           List<Product> products) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.inputParser = inputParser;
        this.products = products;
    }

    public void run() {
        outputView.printWelcome();

        boolean continuesShopping = true;
        while (continuesShopping) {
            try {
                outputView.printProducts(products);
                processPurchase();
                continuesShopping = askContinueShopping();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());

            }
        }
    }
    private void processPurchase() {
        String input = inputView.readPurchaseItem();
        List<PurchaseItem> purchaseItems = inputParser.parsePurchaseItems(input);

        // 나중에 추가할 것들:
        // - 재고 확인
        // - 프로모션 처리
        // - 멤버십 처리
        // - 영수증 출력
    }

    private boolean askContinueShopping() {
        String answer = inputView.readContinueShopping();
        return answer.equals("Y");
    }
}
