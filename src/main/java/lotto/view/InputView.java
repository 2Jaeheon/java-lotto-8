package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String REQUEST_MONEY_MESSAGE = "구입금액을 입력해 주세요.";

    public static String readPurchaseAmount() {
        System.out.println(REQUEST_MONEY_MESSAGE);
        return Console.readLine();
    }
}
