package lotto.domain.lotto;

import java.util.List;
import lotto.global.constant.message.ErrorMessage;
import lotto.global.exception.LottoIllegalArgumentException;

public class Lotto {
    private final List<LottoNumber> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);

        this.numbers = numbers.stream()
                .map(LottoNumber::new)
                .toList();
    }


    public List<LottoNumber> getNumbers() {
        return numbers;
    }

    public boolean containsNumber(LottoNumber number) {
        return numbers.contains(number);
    }

    private void validate(List<Integer> numbers) {
        validateNumbersAmount(numbers);
        validateNumbersDuplication(numbers);
    }

    private static void validateNumbersAmount(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new LottoIllegalArgumentException(ErrorMessage.LOTTO_NUMBER_AMOUNT_ERROR);
        }
    }

    private static void validateNumbersDuplication(List<Integer> numbers) {
        long numberCount = numbers.stream()
                .distinct()
                .count();
        if (numberCount != 6) {
            throw new LottoIllegalArgumentException(ErrorMessage.LOTTO_NUMBER_DUPLICATION_ERROR);
        }
    }

    public void checkDuplicationWithBonusNumber(LottoNumber bonusNumber) {
        if (containsNumber(bonusNumber)) {
            throw new LottoIllegalArgumentException(ErrorMessage.BONUS_NUMBER_DUPLICATION_ERROR);
        }
    }
}
