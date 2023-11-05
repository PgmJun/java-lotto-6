package lotto.domain.lotto;

import java.util.List;
import lotto.domain.GameResult;
import lotto.domain.Money;
import lotto.domain.Rank;
import lotto.domain.lotto.converter.LottoMessageConverter;
import lotto.global.constant.message.ErrorMessage;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoGame {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoMachine lottoMachine;

    public LottoGame(InputView inputView, OutputView outputView, LottoMachine lottoMachine) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoMachine = lottoMachine;
    }


    public void run() {
        Money money = inputMoneyAmount();
        List<Lotto> lottos = purchaseLottos(money);

        Lotto winningLotto = createWinningLotto();
        LottoNumber bonusNumber = createBonusNumber(winningLotto);

        GameResult result = calculateGameResult(winningLotto, bonusNumber, lottos);

        double profitPercentage = calculateProfitPercentage(result.calculateProfit(), money);
        printResult(result, profitPercentage);
    }

    private Money inputMoneyAmount() {
        try {
            int amount = inputView.readLottoPurchaseMoney();
            return new Money(amount);
        } catch (IllegalArgumentException error) {
            outputView.printErrorMessage(error);
            return inputMoneyAmount();
        }
    }

    private List<Lotto> purchaseLottos(Money money) {
        List<Lotto> lottos = lottoMachine.purchaseLottos(money);
        outputView.println(LottoMessageConverter.convertLottoNumberMessage(lottos));

        return lottos;
    }

    private Lotto createWinningLotto() {
        try {
            List<Integer> winningNumbers = inputView.readWinningNumbers();
            return new Lotto(winningNumbers);
        } catch (IllegalArgumentException error) {
            outputView.printErrorMessage(error);
            return createWinningLotto();
        }
    }

    private LottoNumber createBonusNumber(Lotto winningLotto) {
        try {
            int number = inputView.readBonusNumber();
            LottoNumber bonusNumber = new LottoNumber(number);

            if (winningLotto.containsNumber(bonusNumber)) {
                throw new IllegalArgumentException(ErrorMessage.BONUS_NUMBER_DUPLICATION_ERROR);
            }
            return bonusNumber;
        } catch (IllegalArgumentException error) {
            outputView.printErrorMessage(error);
            return createBonusNumber(winningLotto);
        }
    }
}
