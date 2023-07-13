package trade.wayruha.whitebit.domain;

import lombok.Getter;

import java.util.Arrays;

public enum OrderTypeID {
  LIMIT(1),
  MARKET(2),
  MARKET_STOCK(202),
  STOP_LIMIT(3),
  STOP_MARKET(4),
  MARGIN_LIMIT(7),
  MARGIN_MARKET(8),
  MARGIN_STOP_LIMIT(9),
  MARGIN_TRIGGER_STOP_MARKET(10),
  MARGIN_NORMALIZATION(14);

  @Getter
  private final int code;

  OrderTypeID(int code) {
    this.code = code;
  }

  public static OrderTypeID fromCode(int code) {
    return Arrays.stream(values())
        .filter(v -> v.getCode() == code)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown OrderTypeId " + code));
  }
}
