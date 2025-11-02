package lotto.parser;

public class MoneyParser {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_MONEY_IS_NOT_NUMBER = ERROR_PREFIX + "구입 금액은 숫자로 입력해야 합니다";

    /**
     * 이 클래스는 상태를 가지지 않는 순수 함수(문자열 -> 숫자)로만 구성된 유틸리티 클래스입니다.
     * 인스턴스를 생성할 필요가 없으므로 private 생성자를 통해 외부 생성을 막습니다.
     */
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
