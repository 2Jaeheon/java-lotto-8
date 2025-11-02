package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lotto.domain.model.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTest {
    private static final int VALID_AMOUNT = 8000;

    @DisplayName("1000원 단위가 아니면 예외를 발생한다")
    @Test
    void shouldThrowWhenNotMultipleOfThousand() {
        assertThatThrownBy(() -> new Money(1250))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 구입 금액은 1,000원 단위여야 합니다");
    }

    @DisplayName("1000원 미만이면 예외를 발생한다")
    @Test
    void shouldThrowWhenIsLessThan1000() {
        assertThatThrownBy(() -> new Money(900))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 구입 금액은 1,000원 이상이어야 합니다");
    }

    @DisplayName("금액에서 살 수 있는 로또의 장수를 계산한다")
    @Test
    void shouldReturnLottoTicketCount() {
        //given
        Money money = new Money(VALID_AMOUNT);

        //when
        int ticketCount = money.calculateLottoCount();

        //then
        assertThat(ticketCount).isEqualTo(8);
    }
}