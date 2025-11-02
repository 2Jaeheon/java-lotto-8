package lotto.domain.service;

import java.util.ArrayList;
import java.util.List;
import lotto.domain.generator.LottoGenerator;
import lotto.domain.model.Lotto;

public class LottoIssuer {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_NULL = ERROR_PREFIX + "LottoGenerator는 null일 수 없습니다";

    private final LottoGenerator lottoGenerator;

    public LottoIssuer(LottoGenerator generator) {
        validateNotNull(generator);
        this.lottoGenerator = generator;
    }

    public List<Lotto> issue(int count) {
        List<Lotto> lottos = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            List<Integer> lottoNumbers = lottoGenerator.generate();
            lottos.add(new Lotto(lottoNumbers));
        }

        return lottos;
    }

    private void validateNotNull(LottoGenerator generator) {
        if (generator == null) {
            throw new IllegalArgumentException(ERROR_NULL);
        }
    }
}
