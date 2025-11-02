package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lotto.domain.model.Lotto;
import lotto.domain.model.WinningNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WinningNumbersTest {
    @DisplayName("당첨 번호가 6개가 아니면 예외를 발생한다")
    @Test
    void shouldThrowWhenSizeIsNotSix() {
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4), 6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 6개여야 합니다");
    }

    @DisplayName("당첨 번호가 중복되면 예외를 발생한다")
    @Test
    void shouldThrowWhenWinningNumbersDuplicate() {
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 5), 6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 중복될 수 없습니다");
    }

    @DisplayName("당첨 번호가 범위를 벗어나면 예외를 발생한다")
    @Test
    void shouldThrowWhenOutOfRange() {
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 50), 6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 1 ~ 45 사이여야 합니다");
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외를 발생한다")
    @Test
    void shouldThrowWhenBonusDuplicatedWithWinningNumbers() {
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다");
    }

    @DisplayName("로또와 당첨 번호의 일치 개수를 계산한다")
    @Test
    void shouldCountMatchedNumbers() {
        //given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(4, 5, 6, 7, 8, 9), 10);
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        //when
        int count = winningNumbers.countMatches(lotto);

        //then
        assertThat(count).isEqualTo(3);
    }

    @DisplayName("로또와 당첨 번호가 하나도 일치하지 않으면 0을 반환한다")
    @Test
    void shouldReturnZeroWhenNoMatch() {
        //given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(10, 11, 12, 13, 14, 15), 16);
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        //when
        int count = winningNumbers.countMatches(lotto);

        //then
        assertThat(count).isZero();
    }

    @DisplayName("보너스 번호가 로또에 포함되면 true를 반환한다")
    @Test
    void shouldReturnTrueWhenBonusMatched() {
        //given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        Lotto lotto = new Lotto(List.of(2, 7, 9, 11, 15, 20)); // 7 포함

        //when
        boolean matched = winningNumbers.isBonusMatch(lotto);

        //then
        assertThat(matched).isTrue();
    }

    @DisplayName("보너스 번호가 로또에 포함되지 않으면 false를 반환한다")
    @Test
    void shouldReturnFalseWhenBonusNotMatched() {
        //given
        WinningNumbers winningNumbers = new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7);
        Lotto lotto = new Lotto(List.of(2, 8, 9, 11, 15, 20)); // 7 미포함

        //when
        boolean matched = winningNumbers.isBonusMatch(lotto);

        //then
        assertThat(matched).isFalse();
    }
}
