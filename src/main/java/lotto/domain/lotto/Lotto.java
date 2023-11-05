package lotto.domain.lotto;

import java.util.List;
import lotto.global.constant.message.ErrorMessage;

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
            throw new IllegalArgumentException(ErrorMessage.LOTTO_NUMBER_AMOUNT_ERROR);
        }
    }

    private static void validateNumbersDuplication(List<Integer> numbers) {
        long numberCount = numbers.stream()
                .distinct()
                .count();
        if (numberCount != 6) {
            throw new IllegalArgumentException(ErrorMessage.LOTTO_NUMBER_DUPLICATION_ERROR);
        }
    }
}
