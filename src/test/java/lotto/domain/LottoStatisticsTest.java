package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lotto.domain.model.LottoStatistics;
import lotto.domain.model.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoStatisticsTest {

    @DisplayName("Rank 목록에서 Rank별 개수를 계산한다")
    @Test
    void shouldReturnStatisticsFromRanks() {
        //given
        List<Rank> ranks = List.of(
                Rank.FIRST, Rank.FIRST,
                Rank.SECOND,
                Rank.THIRD, Rank.THIRD, Rank.THIRD,
                Rank.FOURTH
        );

        //when
        LottoStatistics statistics = LottoStatistics.createStatistics(ranks);

        //then
        assertThat(statistics.countOf(Rank.FIRST)).isEqualTo(2);
        assertThat(statistics.countOf(Rank.SECOND)).isEqualTo(1);
        assertThat(statistics.countOf(Rank.THIRD)).isEqualTo(3);
        assertThat(statistics.countOf(Rank.FOURTH)).isEqualTo(1);
        assertThat(statistics.countOf(Rank.FIFTH)).isEqualTo(0);
        assertThat(statistics.countOf(Rank.LOSING)).isEqualTo(0);
    }

    @DisplayName("빈 Rank 목록에서 생성 시 모든 Rank 개수는 0이다")
    @Test
    void shouldReturnEmptyStatisticsWhenNoRanks() {
        //given
        List<Rank> ranks = List.of();

        //when
        LottoStatistics statistics = LottoStatistics.createStatistics(ranks);

        //then
        for (Rank rank : Rank.values()) {
            assertThat(statistics.countOf(rank)).isZero();
        }
    }

    @DisplayName("LottoStatistics는 불변 객체이므로 생성 이후 상태가 변하지 않는다")
    @Test
    void shouldThrowWhenImmutableFix() {
        //given
        List<Rank> ranks = List.of(Rank.FIRST);
        LottoStatistics statistics = LottoStatistics.createStatistics(ranks);

        //when
        List<Rank> modifiedRanks = List.of(Rank.FIRST, Rank.SECOND);
        LottoStatistics newStatistics = LottoStatistics.createStatistics(modifiedRanks);

        //then
        assertThat(statistics.countOf(Rank.FIRST)).isEqualTo(1);
        assertThat(statistics.countOf(Rank.SECOND)).isEqualTo(0);

        assertThat(newStatistics.countOf(Rank.FIRST)).isEqualTo(1);
        assertThat(newStatistics.countOf(Rank.SECOND)).isEqualTo(1);
    }

    @DisplayName("총 상금 및 수익률 계산이 정확하다")
    @Test
    void shouldReturnTotalPrizeAndProfitRate() {
        // given
        List<Rank> ranks = List.of(Rank.FIFTH);
        LottoStatistics stats = LottoStatistics.createStatistics(ranks);
        long totalSpent = 8000;

        // when
        long prize = stats.totalPrize();
        double rate = stats.profitRate(totalSpent);

        // then
        assertThat(prize).isEqualTo(Rank.FIFTH.calculatePrize());
        assertThat(rate).isEqualTo(62.5);
    }

    @DisplayName("총 상금 합산을 계산한다")
    @Test
    void shouldCalculateTotalPrize() {
        //given
        List<Rank> ranks = List.of(
                Rank.FIRST, Rank.FIRST,
                Rank.THIRD,
                Rank.FIFTH, Rank.FIFTH, Rank.FIFTH
        );
        LottoStatistics stats = LottoStatistics.createStatistics(ranks);

        //when
        long expected = 2 * Rank.FIRST.calculatePrize()
                + 1 * Rank.THIRD.calculatePrize()
                + 3 * Rank.FIFTH.calculatePrize();

        //then
        assertThat(stats.totalPrize()).isEqualTo(expected);
    }

    @DisplayName("총 지출이 0 이하이면 수익률은 0.0%이다")
    @Test
    void shouldReturnZeroWhenTotalSpentNonPositive() {
        //given
        LottoStatistics stats = LottoStatistics.createStatistics(List.of());

        //when & then
        assertThat(stats.profitRate(0)).isEqualTo(0.0);
        assertThat(stats.profitRate(-1000)).isEqualTo(0.0);
    }

    @DisplayName("수익률을 계산한다")
    @Test
    void shouldComputeProfitRate() {
        //given
        LottoStatistics stats = LottoStatistics.createStatistics(List.of(Rank.FIFTH));

        //when
        long totalSpent = 8000;
        double rate = stats.profitRate(totalSpent);

        //then
        assertThat(rate).isEqualTo(62.5);
    }
}