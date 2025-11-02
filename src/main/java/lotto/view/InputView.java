package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String REQUEST_MONEY_MESSAGE = "구입금액을 입력해 주세요.";
    private static final String REQUEST_WINNING_NUMBER_MESSAGE = "당첨 번호를 입력해 주세요.";
    private static final String REQUEST_BONUS_NUMBER_MESSAGE = "보너스 번호를 입력해 주세요.";

    // 상태를 가지지 않고, 오직 콘솔 입출력만 수행하기 때문에
    // 인스턴스화할 필요가 없는 정적 유틸리티 클래스로 설계하였습니다.
    private InputView() {}

    public static String readPurchaseAmount() {
        System.out.println(REQUEST_MONEY_MESSAGE);
        return Console.readLine();
    }

    public static String readWinningNumbers() {
        System.out.println(REQUEST_WINNING_NUMBER_MESSAGE);
        return Console.readLine();
    }

    public static String readBonusNumber() {
        System.out.println(REQUEST_BONUS_NUMBER_MESSAGE);
        return Console.readLine();
    }
}
