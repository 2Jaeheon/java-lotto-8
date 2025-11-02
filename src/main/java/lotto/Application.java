package lotto;

import lotto.application.AppConfig;
import lotto.application.LottoController;

public class Application {
    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        LottoController controller = config.lottoController();
        controller.run();
    }
}
