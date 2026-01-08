package store.controller;

import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.PurchaseItem;
import store.domain.Receipt;
import store.parser.InputParser;
import store.service.InventoryManager;
import store.service.PromotionCalculator;
import store.service.ReceiptGenerator;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final InputParser inputParser;
    private final List<Product> products;
    private final InventoryManager inventoryManager;
    private final PromotionCalculator promotionCalculator;
    private final ReceiptGenerator receiptGenerator;

    public StoreController(InputView inputView,
                           OutputView outputView,
                           InputParser inputParser,
                           List<Product> products) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.inputParser = inputParser;
        this.products = products;

        this.inventoryManager = new InventoryManager(products);
        this.promotionCalculator = new PromotionCalculator();
        this.receiptGenerator = new ReceiptGenerator(inventoryManager, promotionCalculator);
    }

    public void run() {
        outputView.printWelcome();

        boolean continuesShopping = true;
        while (continuesShopping) {
                outputView.printProducts(products);
            try {
                processPurchase();
                continuesShopping = askContinueShopping();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());

            }
        }
    }
    private void processPurchase() {
        String input = inputView.readPurchaseItems();
        List<PurchaseItem> purchaseItems = inputParser.parsePurchaseItems(input);

        for (PurchaseItem item : purchaseItems) {
            inventoryManager.validateStock(item);
        }

        purchaseItems = processPromotions(purchaseItems);

        boolean useMembership = askMembership();

        Receipt receipt = receiptGenerator.generate(purchaseItems, useMembership);

        outputView.printReceipt(receipt);

        for (PurchaseItem item : purchaseItems) {
            inventoryManager.decreaseStock(item);
        }
        // - 재고 확인
        // - 프로모션 처리
        // - 멤버십 처리
        // - 영수증 출력
    }

    private List<PurchaseItem> processPromotions(List<PurchaseItem> purchaseItems) {
        List<PurchaseItem> result = new ArrayList<>();

        for (PurchaseItem item : purchaseItems) {
            Product product = inventoryManager.findProduct(item.getName());
            int quantity = item.getQuantity();

            // 1개를 더 받을 수 있는 경우
            if (promotionCalculator.canGetOneMore(product, quantity)) {
                String answer = inputView.readPromotionAddition(item.getName());
                if (answer.equals("Y")) {
                    quantity++;
                }
            }

            int nonPromotionQty = promotionCalculator.getNonPromotionQuantity(product, quantity);
            if (nonPromotionQty > 0) {
                String answer = inputView.readPromotionShortage(item.getName(), nonPromotionQty);
                if (answer.equals("N")) {
                    quantity -= nonPromotionQty;
                }
            }

            result.add(new PurchaseItem(item.getName(), quantity));
        }
        return result;
    }

    private boolean askContinueShopping() {
        String answer = inputView.readContinueShopping();
        return answer.equals("Y");
    }

    private boolean askMembership() {
        String answer = inputView.readMembershipDiscount();
        return answer.equals("Y");
    }
}
