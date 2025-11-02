package lotto.domain;

import java.util.EnumMap;
import java.util.List;

public class LottoStatistics {
    private final EnumMap<Rank, Integer> counts;

    private LottoStatistics(EnumMap<Rank, Integer> counts) {
        this.counts = new EnumMap<>(counts);
    }

    /**
     * LottoStatistics는 당첨 결과의 불변 스냅샷을 표현하는 객체입니다.
     * 계산된 Rank 목록으로부터 통계를 생성하며, 이후 상태가 변경되지 않습니다.
     * 불변 객체 특성상 private 생성자와 static factory 메서드를 통해 생성하도록 설계하였습니다.
     */
    public static LottoStatistics createStatistics(List<Rank> ranks) {
        EnumMap<Rank, Integer> map = new EnumMap<>(Rank.class);
        for (Rank r : Rank.values()) {
            map.put(r, 0);
        }
        for (Rank r : ranks) {
            map.put(r, map.get(r) + 1);
        }
        return new LottoStatistics(map);
    }

    public int countOf(Rank rank) {
        return counts.get(rank);
    }

    public long totalPrize() {
        long sum = 0L;
        for (Rank rank : Rank.values()) {
            sum += rank.calculatePrize() * (long) counts.get(rank);
        }
        return sum;
    }

    public double profitRate(long totalSpent) {
        if (totalSpent <= 0) {
            return 0.0;
        }
        return (double) totalPrize() / (double) totalSpent * 100.0;
    }
}
