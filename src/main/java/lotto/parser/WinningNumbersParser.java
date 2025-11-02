package lotto.parser;

import java.util.ArrayList;
import java.util.List;

public class WinningNumbersParser {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_WINNING_FORMAT = ERROR_PREFIX + "당첨 번호는 쉼표(,)로 구분된 6개의 숫자여야 합니다";
    private static final String ERROR_BONUS_FORMAT = ERROR_PREFIX + "보너스 번호는 숫자로 입력해야 합니다";
    private static final int LOTTO_SIZE = 6;
    private static final String DELIMITER = ",";

    private WinningNumbersParser() {
    }

    public static List<Integer> parse(String rawInput) {
        validateNonEmpty(rawInput, ERROR_WINNING_FORMAT);
        String[] tokens = rawInput.split(DELIMITER, -1);
        validateLottoNumberCount(tokens);

        return parseToIntegerList(tokens);
    }

    public static int parseBonus(String rawInput) {
        validateNonEmpty(rawInput, ERROR_BONUS_FORMAT);
        try {
            return Integer.parseInt(rawInput.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_BONUS_FORMAT);
        }
    }

    private static void validateNonEmpty(String rawInput, String message) {
        if (rawInput == null || rawInput.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    private static void validateLottoNumberCount(String[] tokens) {
        if (tokens.length != LOTTO_SIZE) {
            throw new IllegalArgumentException(ERROR_WINNING_FORMAT);
        }
    }

    private static List<Integer> parseToIntegerList(String[] tokens) {
        List<Integer> numbers = new ArrayList<>(LOTTO_SIZE);
        for (String token : tokens) {
            String trimmed = token.trim();

            try {
                numbers.add(Integer.parseInt(trimmed));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(ERROR_WINNING_FORMAT);
            }
        }
        return List.copyOf(numbers);
    }
}
