package lotto.domain;

import lotto.global.constant.message.ErrorMessage;

public class Money {
    private final int amount;

    public Money(int amount) {
        validate(amount);
        this.amount = amount;
    }


    public int getAmount() {
        return amount;
    }

    private void validate(int amount) {
        validateIsAmountZero(amount);
        validateAmountUnit(amount);
    }

    private void validateIsAmountZero(int amount) {
        if (amount == 0) {
            throw new IllegalArgumentException(ErrorMessage.MONEY_AMOUNT_ERROR);
        }
    }

    private void validateAmountUnit(int amount) {
        if (amount % 1000 != 0) {
            throw new IllegalArgumentException(ErrorMessage.MONEY_UNIT_ERROR);
        }
    }
}
