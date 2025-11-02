package lotto.domain.model;

import java.util.List;

public class Lotto {
    private static final int LOTTO_SIZE = 6;
    private static final int MAX_NUMBER = 45;
    private static final int MIN_NUMBER = 1;

    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_LOTTO_SIZE = ERROR_PREFIX + "로또 번호는 6개여야 합니다";
    private static final String ERROR_NUMBER_DUPLICATE = ERROR_PREFIX + "로또 번호는 중복될 수 없습니다";
    private static final String ERROR_NUMBER_RANGE = ERROR_PREFIX + "로또 번호는 1부터 45 사이의 숫자여야 합니다";
    private static final String ERROR_NUMBER_NULL = ERROR_PREFIX + "로또 번호에 null이 포함될 수 없습니다";

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validateNotNull(numbers);
        validateLottoSize(numbers);
        validateDuplicate(numbers);
        validateRange(numbers);

        // 로또 번호는 표현 일관성을 가지기 위해 정렬해서 저장하도록 하였습니다.
        this.numbers = numbers.stream().sorted().toList();
    }

    public List<Integer> numbers() {
        return numbers;
    }

    private void validateLottoSize(List<Integer> numbers) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException(ERROR_LOTTO_SIZE);
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        long distinctCount = numbers.stream().distinct().count();

        if (distinctCount != numbers.size()) {
            throw new IllegalArgumentException(ERROR_NUMBER_DUPLICATE);
        }
    }

    private void validateRange(List<Integer> numbers) {
        // 직관성을 위해 stream 대신 for-each 문을 사용하였습니다.
        for (Integer number : numbers) {
            if (number < MIN_NUMBER || number > MAX_NUMBER) {
                throw new IllegalArgumentException(ERROR_NUMBER_RANGE);
            }
        }
    }

    private void validateNotNull(List<Integer> numbers) {
        if (numbers == null) {
            throw new IllegalArgumentException(ERROR_NUMBER_NULL);
        }

        // 직관성을 위해 stream 대신 for-each 문을 사용하였습니다.
        for (Integer n : numbers) {
            if (n == null) {
                throw new IllegalArgumentException(ERROR_NUMBER_NULL);
            }
        }
    }
}
