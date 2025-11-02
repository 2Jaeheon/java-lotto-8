package lotto.application;

import lotto.domain.service.LottoIssuer;
import lotto.domain.generator.RandomLottoGenerator;
import lotto.domain.service.LottoResultCalculator;
import lotto.domain.service.LottoSeller;
import lotto.domain.generator.LottoGenerator;

/**
 * 애플리케이션의 전체 객체 생성과 의존성 주입(Dependency Injection)을 담당하는 설정 클래스입니다.
 * 객체 간의 구체적인 의존 관계(ex_LottoSeller는 LottoIssuer를 필요로 함)를 이곳에서 조립하여,
 * 각 객체는 자신의 책임(SRP)에만 집중할 수 있도록 합니다.
 */
public class AppConfig {

    public LottoController lottoController() {
        LottoGenerator generator = new RandomLottoGenerator();
        LottoIssuer issuer = new LottoIssuer(generator);
        LottoSeller seller = new LottoSeller(issuer);

        LottoResultCalculator calculator = new LottoResultCalculator();
        return new LottoController(seller, calculator);
    }
}
