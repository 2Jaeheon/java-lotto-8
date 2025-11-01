package lotto.domain;

import java.util.List;

/**
 * LottoSeller는 구매라는 도메인 행위를 담당합니다.
 * 현재는 금액을 받아 로또 발행만 위임하지만, 비즈니스 규칙이 추가될 경우 그 변경을 한 곳에서 처리할 수 있도록 설계되었습니다.
 */
public class LottoSeller {
    private static final String ERROR_LOTTO_ISSUER_NULL = "[ERROR] LottoIssuer는 null일 수 없습니다";
    private static final String ERROR_MONEY_NULL = "[ERROR] Money는 null일 수 없습니다";

    private final LottoIssuer issuer;

    public LottoSeller(LottoIssuer issuer) {
        validate(issuer, ERROR_LOTTO_ISSUER_NULL);
        this.issuer = issuer;
    }

    public List<Lotto> buy(Money money) {
        // Money는 내부 속성을 단순하게 가지고 있으므로, null 검증 책임을 사용처로 분산하였습니다.
        validate(money, ERROR_MONEY_NULL);
        int lottoCount = money.calculateLottoCount();

        return issuer.issue(lottoCount);
    }

    private void validate(Object target, String errorMessage) {
        if (target == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}