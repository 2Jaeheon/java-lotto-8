package lotto.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lotto.domain.generator.LottoGenerator;
import lotto.domain.model.Lotto;
import lotto.domain.service.LottoIssuer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LottoIssuerTest {
    LottoGenerator fakeGenerator;

    @BeforeEach
    void setUp() {
        fakeGenerator = new LottoGenerator() {
            @Override
            public List<Integer> generate() {
                return List.of(1, 2, 3, 4, 5, 6);
            }
        };
    }

    @DisplayName("지정된 개수만큼 로또를 발행한다")
    @Test
    void shouldReturnCorrectNumberOfLottos() {
        //given
        LottoIssuer issuer = new LottoIssuer(fakeGenerator);

        //when
        List<Lotto> lottos = issuer.issue(3);

        //then
        assertThat(lottos).hasSize(3);
    }

    @DisplayName("발행된 로또들은 유효하게 정렬된 번호를 가진다")
    @Test
    void shouldIssueValidLottos() {
        //given
        LottoIssuer issuer = new LottoIssuer(fakeGenerator);

        //when
        List<Lotto> lottos = issuer.issue(2);

        //then
        for (Lotto lotto : lottos) {
            assertThat(lotto.numbers()).containsExactly(1, 2, 3, 4, 5, 6);
        }
    }

    @DisplayName("Generator가 null인 경우 예외를 발생한다")
    @Test
    void shouldThrowWhenNullGenerator() {
        assertThatThrownBy(() -> new LottoIssuer(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] LottoGenerator는 null일 수 없습니다");

    }
}
