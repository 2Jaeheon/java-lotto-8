package lotto.view;

import java.util.List;
import lotto.domain.Lotto;

public class OutputView {
    private static final String PURCHASE_MESSAGE_FORMAT = "%d개를 구매했습니다.";

    public static void printPurchaseCount(int count) {
        System.out.printf(PURCHASE_MESSAGE_FORMAT + "\n", count);
    }

    public static void printPurchasedLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            List<Integer> numbers = lotto.numbers();
            String formatedNumbers = formatNumbers(numbers);
            System.out.println(formatedNumbers);
        }
    }

    /**
     * 출력 형식이 바뀌더라도 도메인에는 영향을 주지 않도록 책임을 분리하기 위해,
     * Lotto의 toString()에 의존하지 않고 View 단에서 별도로 포맷을 담당하도록 설계하였습니다.
     */
    private static String formatNumbers(List<Integer> numbers) {
        return numbers.toString();
    }
}
