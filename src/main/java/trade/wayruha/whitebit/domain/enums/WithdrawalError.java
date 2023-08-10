package trade.wayruha.whitebit.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum WithdrawalError {
  NOT_WITHDRAWABLE(1),
  ADDRESS_INVALID(2),
  AMOUNT_TOO_SMALL(3),
  AMOUNT_TOO_SMALL_FOR_PAYMENT_SYSTEM(4),
  BALANCE_NOT_ENOUGH(5),
  LESS_THEN_FEE(6),
  AMOUNT_MUST_BE_INTEGER(7),
  ZERO_AMOUNT(8),
  ADDRESS_UNAVAILABLE(9);

  @Getter
  @JsonValue
  private final int code;

  WithdrawalError(int code) {
    this.code = code;
  }
}
