package lotto.domain;

public class LottoResultCalculator {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_NULL_LOTTO = ERROR_PREFIX + "Lotto는 null일 수 없습니다";
    private static final String ERROR_NULL_WINNING = ERROR_PREFIX + "WinningNumbers는 null일 수 없습니다";

    public Rank decideRank(Lotto lotto, WinningNumbers winningNumbers) {
        validateNotNull(lotto, ERROR_NULL_LOTTO);
        validateNotNull(winningNumbers, ERROR_NULL_WINNING);

        int matchCount = winningNumbers.countMatches(lotto);
        boolean bonusMatched = winningNumbers.isBonusMatch(lotto);
        return Rank.decide(matchCount, bonusMatched);
    }

    private void validateNotNull(Object target, String message) {
        if (target == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
