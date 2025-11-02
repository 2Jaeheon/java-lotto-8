package lotto.domain.model;

public class Money {
    private static final int PRICE_PER_LOTTO = 1000;
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_BELOW_MIN = ERROR_PREFIX + "구입 금액은 1,000원 이상이어야 합니다";
    private static final String ERROR_NOT_MULTIPLE = ERROR_PREFIX + "구입 금액은 1,000원 단위여야 합니다";

    private final int amount;

    public Money(int amount) {
        validateAmountNotBelowLottoPrice(amount);
        validateAmountIsMultipleOfLottoPrice(amount);
        this.amount = amount;
    }

    public int calculateLottoCount() {
        return amount / PRICE_PER_LOTTO;
    }

    public int amountValue() {
        return amount;
    }

    private void validateAmountIsMultipleOfLottoPrice(int amount) {
        if (amount % PRICE_PER_LOTTO != 0) {
            throw new IllegalArgumentException(ERROR_NOT_MULTIPLE);
        }
    }

    private void validateAmountNotBelowLottoPrice(int amount) {
        if (amount < PRICE_PER_LOTTO) {
            throw new IllegalArgumentException(ERROR_BELOW_MIN);
        }
    }
}
