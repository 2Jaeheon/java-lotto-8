package lotto.view.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lotto.parser.MoneyParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoneyParserTest {
    @DisplayName("사용자가 숫자가 아닌 값을 입력하면 예외를 발생시킨다")
    @Test
    void shouldThrowWhenAmountIsNotNumber() {
        //given
        String input = "!@#";

        //when & then
        assertThatThrownBy(() -> MoneyParser.parse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 구입 금액은 숫자로 입력해야 합니다");
    }

    @DisplayName("사용자가 올바른 숫자를 입력하면 해당 금액을 반환한다")
    @Test
    void shouldReturnAmountNumber() {
        //given
        String input = "100";

        //when
        int parsedNumber = MoneyParser.parse(input);

        //then
        assertThat(parsedNumber).isEqualTo(100);
    }

    @DisplayName("입력이 비어있거나 공백 또는 문자가 섞여있는 경우 예외를 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "a", "10qw", "--1"})
    void shouldThrowOnInvalidInputs(String raw) {
        assertThatThrownBy(() -> MoneyParser.parse(raw))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 구입 금액은 숫자로 입력해야 합니다");
    }
}