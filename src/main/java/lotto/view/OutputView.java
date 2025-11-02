package lotto.view;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoStatistics;
import lotto.domain.Rank;

public class OutputView {
    private static final String PURCHASE_MESSAGE_FORMAT = "%d개를 구매했습니다.";
    private static final String STAT_HEADER = "당첨 통계";
    private static final String STAT_SEPARATOR = "---";

    private static final String STAT_FIFTH_FORMAT = "3개 일치 (%,d원) - %d개\n";
    private static final String STAT_FOURTH_FORMAT = "4개 일치 (%,d원) - %d개\n";
    private static final String STAT_THIRD_FORMAT = "5개 일치 (%,d원) - %d개\n";
    private static final String STAT_SECOND_FORMAT = "5개 일치, 보너스 볼 일치 (%,d원) - %d개\n";
    private static final String STAT_FIRST_FORMAT = "6개 일치 (%,d원) - %d개\n";

    // 상태를 가지지 않고, 오직 콘솔 입출력만 수행하기 때문에
    // 인스턴스화할 필요가 없는 정적 유틸리티 클래스로 설계하였습니다.
    private OutputView() {}

    public static void printPurchaseCount(int count) {
        System.out.printf(PURCHASE_MESSAGE_FORMAT + "\n", count);
    }

    public static void printPurchasedLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            List<Integer> numbers = lotto.numbers();
            String formattedNumbers = formatNumbers(numbers);
            System.out.println(formattedNumbers);
        }
    }

    /**
     * 출력 형식은 도메인이 아닌 View단에서 처리하였습니다.
     * 도메인은 무엇을 계산할지에 대해서 집중하고,
     * 어떻게 보여줄지는 View에서 처리해야 한다고 생각했습니다.
     * 따라서 출력 문구는 OutputView에서 처리하였습니다.
     */
    public static void printLottoStatistics(LottoStatistics statistics) {
        printStatisticsHeader();

        printStatLine(Rank.FIFTH, statistics.countOf(Rank.FIFTH));
        printStatLine(Rank.FOURTH, statistics.countOf(Rank.FOURTH));
        printStatLine(Rank.THIRD, statistics.countOf(Rank.THIRD));
        printStatLine(Rank.SECOND, statistics.countOf(Rank.SECOND));
        printStatLine(Rank.FIRST, statistics.countOf(Rank.FIRST));
    }

    private static void printStatisticsHeader() {
        System.out.println(STAT_HEADER);
        System.out.println(STAT_SEPARATOR);
    }

    private static void printStatLine(Rank rank, int count) {
        if (rank == Rank.FIFTH) {
            System.out.printf(STAT_FIFTH_FORMAT, rank.calculatePrize(), count);
            return;
        }
        if (rank == Rank.FOURTH) {
            System.out.printf(STAT_FOURTH_FORMAT, rank.calculatePrize(), count);
            return;
        }
        if (rank == Rank.THIRD) {
            System.out.printf(STAT_THIRD_FORMAT, rank.calculatePrize(), count);
            return;
        }
        if (rank == Rank.SECOND) {
            System.out.printf(STAT_SECOND_FORMAT, rank.calculatePrize(), count);
            return;
        }
        if (rank == Rank.FIRST) {
            System.out.printf(STAT_FIRST_FORMAT, rank.calculatePrize(), count);
            return;
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
