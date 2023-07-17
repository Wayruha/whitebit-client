package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum OrderType {

  LIMIT(1, "limit"),
  MARKET(2, "market"),
  MARKET_STOCK(202),
  STOP_LIMIT(3),
  STOP_MARKET(4),
  MARGIN_LIMIT(7),
  MARGIN_MARKET(8),
  MARGIN_STOP_LIMIT(9),
  MARGIN_TRIGGER_STOP_MARKET(10),
  MARGIN_NORMALIZATION(14);

  private final int code;
  private final String name;

  OrderType(int code, String name) {
    this.code = code;
    this.name = name;
  }

  OrderType(int code) {
    this.code = code;
    this.name = null;
  }

  @JsonValue
  public String getName() {
    return name;
  }

  public static OrderType fromCode(int code) {
    return Arrays.stream(values())
        .filter(type -> code == type.code)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown OrderType code:" + code));
  }

  public static OrderType fromName(String name) {
    return Arrays.stream(values())
        .filter(type -> name.equalsIgnoreCase(type.name))
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown OrderType name:" + name));
  }
}
