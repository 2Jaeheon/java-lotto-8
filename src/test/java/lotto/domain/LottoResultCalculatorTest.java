package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lotto.domain.model.Lotto;
import lotto.domain.model.LottoStatistics;
import lotto.domain.model.Rank;
import lotto.domain.model.WinningNumbers;
import lotto.domain.service.LottoResultCalculator;
import lotto.parser.WinningNumbersParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LottoResultCalculatorTest {
    private LottoResultCalculator calculator;
    private WinningNumbers winning;

    @BeforeEach
    void setUp() {
        calculator = new LottoResultCalculator();
        winning = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
    }

    @DisplayName("당첨 번호 일치 개수 및 보너스 여부에 따라 Rank가 결정된다")
    @ParameterizedTest(name = "{0}: {1}")
    @CsvSource(delimiter = '|', value = {
            "1, 2, 3, 4, 5, 6 | FIRST",
            "1, 2, 3, 4, 5, 7 | SECOND",
            "1, 2, 3, 4, 5, 45 | THIRD",
            "1, 2, 3, 4, 45, 44 | FOURTH",
            "1, 2, 3, 45, 44, 43 | FIFTH",
            "1, 2, 45, 44, 43, 42 | LOSING",
            "1, 45, 44, 43, 42, 7 | LOSING"
    })
    void shouldDecideRankByNumbers(String lottoNumbers, Rank expected) {
        Lotto lotto = new Lotto(WinningNumbersParser.parse(lottoNumbers));
        Rank result = calculator.decideRank(lotto, winning);
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("lotto가 null이면 예외를 던진다")
    @Test
    void shouldThrowWhenLottoIsNull() {
        assertThatThrownBy(() -> calculator.decideRank(null, winning))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또는 null일 수 없습니다");
    }

    @DisplayName("winningNumbers가 null이면 예외를 던진다")
    @Test
    void shouldThrowWhenWinningNumbersIsNull() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThatThrownBy(() -> calculator.decideRank(lotto, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 null일 수 없습니다");
    }

    @DisplayName("여러 장의 로또를 채점해 Rank별 개수를 집계한다")
    @Test
    void shouldAggregateCountsForEachRankOnce() {
        //given
        List<Lotto> lottos = List.of(
                new Lotto(WinningNumbersParser.parse("1,2,3,4,5,6")),
                new Lotto(WinningNumbersParser.parse("1,2,3,4,5,7")),
                new Lotto(WinningNumbersParser.parse("1,2,3,4,5,45")),
                new Lotto(WinningNumbersParser.parse("1,2,3,4,45,44")),
                new Lotto(WinningNumbersParser.parse("1,2,3,45,44,43")),
                new Lotto(WinningNumbersParser.parse("1,45,44,43,42,41"))
        );

        //when
        LottoStatistics statistics = calculator.calculateStatistics(lottos, winning);

        //then
        assertThat(statistics.countOf(Rank.FIRST)).isEqualTo(1);
        assertThat(statistics.countOf(Rank.SECOND)).isEqualTo(1);
        assertThat(statistics.countOf(Rank.THIRD)).isEqualTo(1);
        assertThat(statistics.countOf(Rank.FOURTH)).isEqualTo(1);
        assertThat(statistics.countOf(Rank.FIFTH)).isEqualTo(1);
        assertThat(statistics.countOf(Rank.LOSING)).isEqualTo(1);
    }

    @DisplayName("동일 Rank가 여러 번 등장하면 해당 Rank 개수를 누적 집계한다")
    @Test
    void shouldAggregateMultipleSameRank() {
        //given
        List<Lotto> lottos = List.of(
                new Lotto(WinningNumbersParser.parse("1,2,3,40,41,42")),
                new Lotto(WinningNumbersParser.parse("1,2,3,10,11,12")),
                new Lotto(WinningNumbersParser.parse("1,2,3,20,21,22"))
        );

        //when
        LottoStatistics stats = calculator.calculateStatistics(lottos, winning);

        //then
        assertThat(stats.countOf(Rank.FIFTH)).isEqualTo(3);
        assertThat(stats.countOf(Rank.FOURTH)).isEqualTo(0);
        assertThat(stats.countOf(Rank.LOSING)).isEqualTo(0);
    }

    @DisplayName("로또 목록이 null이면 예외를 발생한다")
    @Test
    void shouldThrowWhenLottosIsNull() {
        // given
        WinningNumbers winning = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        LottoResultCalculator calculator = new LottoResultCalculator();

        // expect
        assertThatThrownBy(() -> calculator.calculateStatistics(null, winning))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 목록은 null일 수 없습니다");
    }
}
