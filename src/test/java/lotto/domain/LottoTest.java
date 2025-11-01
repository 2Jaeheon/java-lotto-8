package lotto.domain;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    @DisplayName("로또 번호가 6개가 아니면 예외를 발생한다")
    @Test
    void shouldThrowWhenSizeIsNotSix() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 6개여야 합니다");
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다")
    @Test
    void shouldThrowWhenDuplicatedNumber() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호에 중복이 존재합니다");
    }

    @DisplayName("로또 번호가 1~45를 벗어나면 예외를 발생한다")
    @Test
    void shouldThrowWhenOutOfRange() {
        assertThatThrownBy(() -> new Lotto(List.of(0, 2, 3, 4, 5, 48)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호는 1 ~ 45 사이여야 합니다");
    }

    @DisplayName("로또가 NULL인 경우 예외를 발생한다")
    @Test
    void shouldThrowWhenListIsNull() {
        assertThatThrownBy(() -> new Lotto(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호에 null이 포함될 수 없습니다");
    }

    @DisplayName("로또 번호가 NULL인 경우 예외를 발생한다")
    @Test
    void shouldThrowWhenLottoNumberIsNull() {
        //given
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        numbers.add(null);

        //when & then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 로또 번호에 null이 포함될 수 없습니다");
    }
}
