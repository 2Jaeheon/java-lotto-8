package lotto.application;

import lotto.domain.service.LottoIssuer;
import lotto.domain.generator.RandomLottoGenerator;
import lotto.domain.service.LottoResultCalculator;
import lotto.domain.service.LottoSeller;
import lotto.domain.generator.LottoGenerator;

public class AppConfig {

    public LottoController lottoController() {
        LottoGenerator generator = new RandomLottoGenerator();
        LottoIssuer issuer = new LottoIssuer(generator);
        LottoSeller seller = new LottoSeller(issuer);

        LottoResultCalculator calculator = new LottoResultCalculator();
        return new LottoController(seller, calculator);
    }
}
