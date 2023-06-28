package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderType {
  LIMIT("limit"), MARKET("market");

  private final String name;

  OrderType(String name) {
    this.name = name;
  }

  @JsonValue
  public String getName() {
    return name;
  }
}
