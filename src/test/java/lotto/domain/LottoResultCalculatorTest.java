package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import lotto.view.converter.WinningNumbersParser;
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
        Lotto lotto = new Lotto(WinningNumbersParser.parseWinningNumbers(lottoNumbers));
        Rank result = calculator.decideRank(lotto, winning);
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("lotto가 null이면 예외를 던진다")
    @Test
    void shouldThrowWhenLottoIsNull() {
        assertThatThrownBy(() -> calculator.decideRank(null, winning))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] Lotto는 null일 수 없습니다");
    }

    @DisplayName("winningNumbers가 null이면 예외를 던진다")
    @Test
    void shouldThrowWhenWinningNumbersIsNull() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThatThrownBy(() -> calculator.decideRank(lotto, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] WinningNumbers는 null일 수 없습니다");
    }
}