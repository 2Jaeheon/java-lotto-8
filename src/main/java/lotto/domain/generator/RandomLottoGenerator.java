package lotto.domain.generator;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;

public class RandomLottoGenerator implements LottoGenerator {
    @Override
    public List<Integer> generate() {
        return Randoms.pickUniqueNumbersInRange(1, 45, 6)
                .stream()
                .toList();
    }
}
