package trade.wayruha.whitebit.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum TransactionMethod {
  DEPOSIT(1), WITHDRAW(2);

  TransactionMethod(int code) {
    this.code = code;
  }

  @Getter
  @JsonValue
  private final int code;
}
