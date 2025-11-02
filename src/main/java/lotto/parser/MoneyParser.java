package lotto.parser;

public class MoneyParser {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_MONEY_IS_NOT_NUMBER = ERROR_PREFIX + "금액은 숫자여야 합니다";

    private MoneyParser() {
    }

    public static int parse(String rawInput) {
        try {
            return Integer.parseInt(rawInput.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_MONEY_IS_NOT_NUMBER);
        }
    }
}
