package lotto.domain;

import java.util.ArrayList;
import java.util.List;

public class LottoResultCalculator {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_LOTTO_NULL = ERROR_PREFIX + "로또는 null일 수 없습니다";
    private static final String ERROR_WINNING_NULL = ERROR_PREFIX + "당첨 번호는 null일 수 없습니다";
    private static final String ERROR_LOTTOS_NULL = ERROR_PREFIX + "로또 목록은 null일 수 없습니다";

    public Rank decideRank(Lotto lotto, WinningNumbers winningNumbers) {
        validateNotNull(lotto, ERROR_LOTTO_NULL);
        validateNotNull(winningNumbers, ERROR_WINNING_NULL);

        int matchCount = winningNumbers.countMatches(lotto);
        boolean bonusMatched = winningNumbers.isBonusMatch(lotto);
        return Rank.decide(matchCount, bonusMatched);
    }

    public LottoStatistics calculateStatistics(List<Lotto> lottos, WinningNumbers winningNumbers) {
        validateNotNull(lottos, ERROR_LOTTOS_NULL);
        validateNotNull(winningNumbers, ERROR_WINNING_NULL);

        List<Rank> ranks = new ArrayList<>(lottos.size());
        for (Lotto lotto : lottos) {
            ranks.add(decideRank(lotto, winningNumbers));
        }
        return LottoStatistics.createStatistics(ranks);
    }

    private void validateNotNull(Object target, String message) {
        if (target == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
