package lotto.domain;

import java.util.ArrayList;
import java.util.List;

public class LottoIssuer {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_NULL = ERROR_PREFIX + "NumberGenerator는 null일 수 없습니다";

    private final NumberGenerator lottoNumbergenerator;

    public LottoIssuer(NumberGenerator generator) {
        validateNotNull(generator);
        this.lottoNumbergenerator = generator;
    }

    public List<Lotto> issue(int count) {
        List<Lotto> lottos = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            List<Integer> lottoNumbers = lottoNumbergenerator.generate();
            lottos.add(new Lotto(lottoNumbers));
        }

        return lottos;
    }

    private void validateNotNull(NumberGenerator generator) {
        if (generator == null) {
            throw new IllegalArgumentException(ERROR_NULL);
        }
    }
}
