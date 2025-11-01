package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
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
}