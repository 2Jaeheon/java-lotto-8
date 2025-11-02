package lotto.application;

import java.util.List;
import lotto.domain.model.Lotto;
import lotto.domain.service.LottoResultCalculator;
import lotto.domain.service.LottoSeller;
import lotto.domain.model.LottoStatistics;
import lotto.domain.model.Money;
import lotto.domain.model.WinningNumbers;
import lotto.view.InputView;
import lotto.view.OutputView;
import lotto.parser.MoneyParser;
import lotto.parser.WinningNumbersParser;

public class LottoController {
    private final LottoSeller lottoSeller;
    private final LottoResultCalculator calculator;

    public LottoController(LottoSeller lottoSeller, LottoResultCalculator calculator) {
        this.lottoSeller = lottoSeller;
        this.calculator = calculator;
    }

    public void run() {
        // 입력
        Money money = requestMoney();
        List<Lotto> purchasedLottos = lottoSeller.buy(money);
        printPurchase(purchasedLottos);

        // 처리
        WinningNumbers winningNumbers = requestWinningNumbers();
        LottoStatistics statistics = calculator.calculateStatistics(purchasedLottos, winningNumbers);

        // 출력
        printResults(statistics, money);
    }

    /**
     * 유효한 Money 객체가 생성될 때까지 구입 금액 입력을 재시도합니다.
     * Money 생성자가 스스로 모든 유효성 검증을 책임지므로,
     * 별도 팩토리 없이 컨트롤러가 직접 생성 흐름을 제어합니다.
     */
    private Money requestMoney() {
        while (true) {
            try {
                String rawAmount = InputView.readPurchaseAmount();
                int amount = MoneyParser.parse(rawAmount);
                return new Money(amount);
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private void printPurchase(List<Lotto> purchased) {
        OutputView.printPurchaseCount(purchased.size());
        OutputView.printPurchasedLottos(purchased);
    }

    /**
     * 유효한 WinningNumbers 객체가 생성될 때까지 당첨/보너스 번호 입력을 재시도합니다.
     * WinningNumbers 생성자가 모든 유효성 검증(중복, 범위 등)을 전담하므로,
     * 별도 팩토리 없이 컨트롤러가 직접 생성 흐름을 제어합니다.
     */
    private WinningNumbers requestWinningNumbers() {
        while (true) {
            try {
                String rawWinning = InputView.readWinningNumbers();
                List<Integer> numbers = WinningNumbersParser.parse(rawWinning);

                String rawBonus = InputView.readBonusNumber();
                int bonus = WinningNumbersParser.parseBonus(rawBonus);

                return new WinningNumbers(numbers, bonus);
            } catch (IllegalArgumentException e) {
                OutputView.printError(e.getMessage());
            }
        }
    }

    private void printResults(LottoStatistics stats, Money money) {
        OutputView.printLottoStatistics(stats);
        double profitRate = stats.profitRate(money.amountValue());
        OutputView.printProfitRate(profitRate);
    }
}
