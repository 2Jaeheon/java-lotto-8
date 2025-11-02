package lotto.application;

import lotto.domain.Lotto;
import lotto.domain.LottoIssuer;
import lotto.domain.LottoNumberGenerator;
import lotto.domain.LottoResultCalculator;
import lotto.domain.LottoSeller;
import lotto.domain.NumberGenerator;

public class AppConfig {

    public LottoController lottoController() {
        NumberGenerator generator = new LottoNumberGenerator();
        LottoIssuer issuer = new LottoIssuer(generator);
        LottoSeller seller = new LottoSeller(issuer);

        LottoResultCalculator calculator = new LottoResultCalculator();
        return new LottoController(seller, calculator);
    }
}
