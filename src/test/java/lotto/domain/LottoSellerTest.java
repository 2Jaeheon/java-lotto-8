package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoSellerTest {

    LottoIssuer fakeIssuer;

    @BeforeEach
    void setUp() {
        fakeIssuer = new LottoIssuer(new NumberGenerator() {
            @Override
            public List<Integer> generate() {
                return List.of(1, 2, 3, 4, 5, 6);
            }
        });
    }

    @DisplayName("계산된 개수만큼 로또를 발행해야 한다")
    @Test
    void shouldIssueLottoWhenGivenValidMoney() {
        //given
        LottoSeller seller = new LottoSeller(fakeIssuer);
        Money money = new Money(3000);

        //when
        List<Lotto> lottos = seller.buy(money);

        //then
        assertThat(lottos).hasSize(3);
    }

    @DisplayName("LottoIssuer가 null이면 예외를 발생한다")
    @Test
    void shouldThrowWhenIssuerIsNull() {
        assertThatThrownBy(() -> new LottoSeller(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] LottoIssuer는 null일 수 없습니다");
    }

    @DisplayName("Money가 null이면 예외를 발생한다")
    @Test
    void shouldThrowWhenMoneyIsNull() {
        //given
        LottoSeller seller = new LottoSeller(fakeIssuer);

        //when & then
        assertThatThrownBy(() -> seller.buy(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] Money는 null일 수 없습니다");
    }
}