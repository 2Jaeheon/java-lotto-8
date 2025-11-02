package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;

import lotto.domain.model.Rank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RankTest {
    @DisplayName("일치 개수와 보너스 여부에 따라 올바른 등수를 반환한다")
    @ParameterizedTest(name = "matchCount={0}, bonusMatched={1}: expected={2}")
    @CsvSource({
            "6, false, FIRST",
            "5, true, SECOND",
            "5, false, THIRD",
            "4, false, FOURTH",
            "3, true, FIFTH",
            "2, false, LOSING",
            "0, true, LOSING"
    })
    void shouldReturnRankByMatchCountAndBonus(int matchCount, boolean bonusMatched, Rank expected) {
        //when
        Rank result = Rank.decide(matchCount, bonusMatched);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("각 Rank는 올바른 상금을 가진다")
    @ParameterizedTest(name = "prize={1}")
    @CsvSource({
            "FIRST, 2000000000",
            "SECOND, 30000000",
            "THIRD, 1500000",
            "FOURTH, 50000",
            "FIFTH, 5000",
            "LOSING, 0"
    })
    void shouldHaveCorrectPrize(Rank rank, long expectedPrize) {
        // when
        long prize = rank.calculatePrize();

        // then
        assertThat(prize).isEqualTo(expectedPrize);
    }
}