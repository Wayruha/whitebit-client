package trade.wayruha.whitebit.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum DepositStatus {
  SUCCESSFUL(3, 7),
  CANCELED(4, 9),
  UNCONFIRMED(5),
  UNCREDITED(22),
  PENDING(15);

  @Getter
  private final List<Integer> statusCodes;

  DepositStatus(Integer... statusCodes) {
    this.statusCodes = List.of(statusCodes);
  }

  public static DepositStatus fromCode(int code){
    return Arrays.stream(values()).filter(status -> status.statusCodes.contains(code))
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown Deposit Status code: " + code));
  }
}
