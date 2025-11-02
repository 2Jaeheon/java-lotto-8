package lotto.application;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.LottoResultCalculator;
import lotto.domain.LottoSeller;
import lotto.domain.LottoStatistics;
import lotto.domain.Money;
import lotto.domain.WinningNumbers;
import lotto.view.InputView;
import lotto.view.OutputView;
import lotto.view.converter.MoneyParser;
import lotto.view.converter.WinningNumbersParser;

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
        printPurchase(purchasedLottos, money);

        // 처리
        WinningNumbers winningNumbers = requestWinningNumbers();
        LottoStatistics statistics = calculator.calculateStatistics(purchasedLottos, winningNumbers);

        // 출력
        printResults(statistics, money);
    }

    // 금액에 대해 문자열 파싱은 Parser에서 일원화하고,
    // 값 검증은 도메인(Money)에 위임.
    private Money requestMoney() {
        String rawAmount = InputView.readPurchaseAmount();
        int amount = MoneyParser.parse(rawAmount);
        return new Money(amount);
    }

    private void printPurchase(List<Lotto> purchased, Money money) {
        OutputView.printPurchaseCount(money.calculateLottoCount());
        OutputView.printPurchasedLottos(purchased);
    }

    // 당첨, 보너스 번호에 대한 문자열 파싱은 Parser에서 일원화하고,
    // 값 검증은 도메인(WinningNumbers)에 위임.
    private WinningNumbers requestWinningNumbers() {
        String rawWinning = InputView.readWinningNumbers();
        List<Integer> numbers = WinningNumbersParser.parseWinningNumbers(rawWinning);

        String rawBonus = InputView.readBonusNumber();
        int bonus = WinningNumbersParser.parseBonusNumber(rawBonus);

        return new WinningNumbers(numbers, bonus);
    }

    // View에 표현 규칙을 위임하고, Controller는 값만 전달하도록 구현.
    private void printResults(LottoStatistics stats, Money money) {
        OutputView.printLottoStatistics(stats);
        double profitRate = stats.profitRate(money.amountValue());
        OutputView.printProfitRate(profitRate);
    }
}
