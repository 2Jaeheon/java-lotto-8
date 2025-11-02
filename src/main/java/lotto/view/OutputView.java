package lotto.view;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lotto.domain.model.Lotto;
import lotto.domain.model.LottoStatistics;
import lotto.domain.model.Rank;

/** 이 클래스는 LottoStatistics, Lotto 같은 도메인 모델을 직접 참조(의존)합니다
 *  현재 View는 데이터를 읽기 전용(Read-Only)으로만 사용하기 때문에,
 *  프로젝트 규모상 DTO를 도입하는 것은 불필요한 복잡성을 야기한다고 판단했습니다.
 */
public class OutputView {
    private static final String PURCHASE_MESSAGE_FORMAT = "%d개를 구매했습니다.";
    private static final String STAT_HEADER = "당첨 통계";
    private static final String STAT_SEPARATOR = "---";
    private static final String PROFIT_RATE_FORMAT = "총 수익률은 %.1f%%입니다.\n";

    private static final String STAT_FIFTH_FORMAT = "3개 일치 (%,d원) - %d개\n";
    private static final String STAT_FOURTH_FORMAT = "4개 일치 (%,d원) - %d개\n";
    private static final String STAT_THIRD_FORMAT = "5개 일치 (%,d원) - %d개\n";
    private static final String STAT_SECOND_FORMAT = "5개 일치, 보너스 볼 일치 (%,d원) - %d개\n";
    private static final String STAT_FIRST_FORMAT = "6개 일치 (%,d원) - %d개\n";

    private static final Map<Rank, String> RANK_FORMAT_MAP = new EnumMap<>(Rank.class);

    static {
        RANK_FORMAT_MAP.put(Rank.FIFTH, STAT_FIFTH_FORMAT);
        RANK_FORMAT_MAP.put(Rank.FOURTH, STAT_FOURTH_FORMAT);
        RANK_FORMAT_MAP.put(Rank.THIRD, STAT_THIRD_FORMAT);
        RANK_FORMAT_MAP.put(Rank.SECOND, STAT_SECOND_FORMAT);
        RANK_FORMAT_MAP.put(Rank.FIRST, STAT_FIRST_FORMAT);
    }

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

    public static void printLottoStatistics(LottoStatistics statistics) {
        printStatisticsHeader();

        printStatLine(Rank.FIFTH, statistics.countOf(Rank.FIFTH));
        printStatLine(Rank.FOURTH, statistics.countOf(Rank.FOURTH));
        printStatLine(Rank.THIRD, statistics.countOf(Rank.THIRD));
        printStatLine(Rank.SECOND, statistics.countOf(Rank.SECOND));
        printStatLine(Rank.FIRST, statistics.countOf(Rank.FIRST));
    }

    public static void printProfitRate(double profitRate) {
        System.out.printf(PROFIT_RATE_FORMAT, profitRate);
    }

    public static void printError(String message) {
        System.out.println(message);
    }

    private static void printStatisticsHeader() {
        System.out.println(STAT_HEADER);
        System.out.println(STAT_SEPARATOR);
    }

    private static void printStatLine(Rank rank, int count) {
        String format = RANK_FORMAT_MAP.get(rank);

        if (format != null) {
            System.out.printf(format, rank.calculatePrize(), count);
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
