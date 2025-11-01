package lotto.domain;

import java.util.List;

public class WinningNumbers {
    private static final int SIZE = 6;
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_SIZE = ERROR_PREFIX + "당첨 번호는 6개여야 합니다";
    private static final String ERROR_DUPLICATE = ERROR_PREFIX + "당첨 번호에 중복이 존재합니다";
    private static final String ERROR_RANGE = ERROR_PREFIX + "당첨 번호는 1 ~ 45 사이여야 합니다";
    private static final String ERROR_BONUS_DUPLICATE = ERROR_PREFIX + "보너스 번호가 당첨 번호와 중복됩니다";
    private static final String ERROR_NULL = ERROR_PREFIX + "당첨 번호에 null이 포함될 수 없습니다";

    private final List<Integer> numbers;
    private final int bonus;

    public WinningNumbers(List<Integer> numbers, int bonus) {
        validateNull(numbers);
        validateSize(numbers);
        validateDuplicate(numbers);
        validateRange(numbers);
        validateBonus(bonus, numbers);

        // 당첨 번호는 표현 일관성을 가지기 위해 정렬해서 저장하도록 하였습니다.
        this.numbers = numbers.stream().sorted().toList();
        this.bonus = bonus;
    }

    public int countMatches(Lotto lotto) {
        int count = 0;
        for (int n : lotto.numbers()) {
            if (this.numbers.contains(n)) {
                count++;
            }
        }
        return count;
    }

    public boolean isBonusMatch(Lotto lotto) {
        return lotto.numbers().contains(this.bonus);
    }

    private void validateNull(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException(ERROR_NULL);
        }
        for (Integer number : numbers) {
            if (number == null) {
                throw new IllegalArgumentException(ERROR_NULL);
            }
        }
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != SIZE) {
            throw new IllegalArgumentException(ERROR_SIZE);
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        long distinctCount = numbers.stream().distinct().count();
        if (distinctCount != numbers.size()) {
            throw new IllegalArgumentException(ERROR_DUPLICATE);
        }
    }

    private void validateRange(List<Integer> numbers) {
        for (int number : numbers) {
            if (number < MIN_NUMBER || number > MAX_NUMBER) {
                throw new IllegalArgumentException(ERROR_RANGE);
            }
        }
    }

    private void validateBonus(int bonus, List<Integer> numbers) {
        if (bonus < MIN_NUMBER || bonus > MAX_NUMBER) {
            throw new IllegalArgumentException(ERROR_RANGE);
        }

        if (numbers.contains(bonus)) {
            throw new IllegalArgumentException(ERROR_BONUS_DUPLICATE);
        }
    }
}
