package trade.wayruha.whitebit.domain;

import java.util.Arrays;

public enum OrderEventType {
  NEW(1),
  UPDATE(2),
  FINISH(3);

  private final int code;

  OrderEventType(int code) {
    this.code = code;
  }

  public static OrderEventType fromCode(int code) {
    return Arrays.stream(values())
        .filter(type -> type.code == code)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown OrderEventType code:" + code));
  }
}
