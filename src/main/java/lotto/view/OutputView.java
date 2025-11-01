package lotto.view;

public class OutputView {
    private static final String PURCHASE_MESSAGE_FORMAT = "%d개를 구매했습니다.";

    public static void printPurchaseCount(int count) {
        System.out.printf(PURCHASE_MESSAGE_FORMAT + "\n", count);
    }
}
