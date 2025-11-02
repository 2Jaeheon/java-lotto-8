package lotto.domain.service;

import java.util.List;
import java.util.stream.IntStream;
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
        return IntStream.range(0, count)
                .mapToObj(i -> lottoGenerator.generate())
                .map(Lotto::new)
                .toList();
    }

    private void validateNotNull(LottoGenerator generator) {
        if (generator == null) {
            throw new IllegalArgumentException(ERROR_NULL);
        }
    }
}
