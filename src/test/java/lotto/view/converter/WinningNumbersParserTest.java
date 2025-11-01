package lotto.view.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class WinningNumbersParserTest {

    @DisplayName("정상 입력은 6개의 숫자 리스트로 변환된다 (공백 포함)")
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
            "'1,2,3,4,5,6', '[1, 2, 3, 4, 5, 6]'",
            "' 1, 2 , 3 ,4,5 ,6 ', '[1, 2, 3, 4, 5, 6]'",
            "'10,20,30,40,41,42', '[10, 20, 30, 40, 41, 42]'"
    })
    void shouldParseValidWinningNumbers(String input, String expectedListString) {
        // when
        List<Integer> result = WinningNumbersParser.parseWinningNumbers(input);

        // then
        assertThat(result.toString()).isEqualTo(expectedListString);
    }

    @DisplayName("입력이 null 또는 비어 있으면 예외를 발생한다")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void shouldThrowWhenInputIsNullOrEmpty(String input) {
        assertThatThrownBy(() -> WinningNumbersParser.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 쉼표(,)로 구분된 6개의 숫자여야 합니다");
    }

    @DisplayName("숫자가 아닌 값이 포함되면 예외를 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,x", "1,2,3,4,5,@", "1,2,3,4, ,6"})
    void shouldThrowWhenNonNumericTokenExists(String input) {
        assertThatThrownBy(() -> WinningNumbersParser.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 쉼표(,)로 구분된 6개의 숫자여야 합니다");
    }

    @DisplayName("숫자 개수가 6개가 아니면 예외를 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5", "1,2,3,4,5,6,7"})
    void shouldThrowWhenLottoSizeIsInvalid(String input) {
        assertThatThrownBy(() -> WinningNumbersParser.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 쉼표(,)로 구분된 6개의 숫자여야 합니다");
    }

    @DisplayName("컴마가 맨 앞 또는 맨 마지막에 있는 경우 예외를 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,6,", ",1,2,3,4,5,6"})
    void shouldThrowWhenLeadingOrTrailingComma(String input) {
        assertThatThrownBy(() -> WinningNumbersParser.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 쉼표(,)로 구분된 6개의 숫자여야 합니다");
    }

    @DisplayName("콤마 연속 포함 시 예외를 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"1,,3,4,5,6"})
    void shouldThrowWhenEmptyTokenExists(String input) {
        assertThatThrownBy(() -> WinningNumbersParser.parseWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 당첨 번호는 쉼표(,)로 구분된 6개의 숫자여야 합니다");
    }

    // 아래는 보너스 번호 테스트 코드입니다.

    @DisplayName("보너스 번호가 공백 포함된 정상 문자인 경우 정수를 반환한다")
    @ParameterizedTest
    @ValueSource(strings = {"7", " 7 ", "45"})
    void shouldParseBonusNumberWithSpaces(String input) {
        int n = WinningNumbersParser.parseBonusNumber(input);
        assertThat(n).isIn(7, 45);
    }

    @DisplayName("보너스 번호에 문자가 있는 경우 예외를 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"--1", "1x", "%"})
    void shouldThrowWhenBonusIsNonNumeric(String input) {
        assertThatThrownBy(() -> WinningNumbersParser.parseBonusNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 보너스 번호는 숫자여야 합니다");
    }

    @DisplayName("보너스 번호가 null 또는 빈 문자열이면 예외")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   "})
    void shouldThrowWhenBonusIsNullOrEmpty(String input) {
        assertThatThrownBy(() -> WinningNumbersParser.parseBonusNumber(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 보너스 번호는 숫자여야 합니다");
    }
}