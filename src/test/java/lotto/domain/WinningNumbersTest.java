package lotto.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
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
                .hasMessage("[ERROR] 당첨 번호에 중복이 존재합니다");
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
                .hasMessage("[ERROR] 보너스 번호가 당첨 번호와 중복됩니다");
    }
}
