package trade.wayruha.whitebit.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum WithdrawalStatus {
  PENDING(1, 2, 6, 10, 11, 12, 13, 14, 15, 16, 17),
  SUCCESSFUL(3, 7),
  CANCELED(4),
  UNCONFIRMED(5),
  PARTIALLY_SUCCESSFUL(18);

  @Getter
  private final List<Integer> statusCodes;

  WithdrawalStatus(Integer... statusCodes) {
    this.statusCodes = List.of(statusCodes);
  }

  public static WithdrawalStatus fromCode(int code) {
    return Arrays.stream(values()).filter(status -> status.statusCodes.contains(code))
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown Withdraw Status code: " + code));
  }
}
