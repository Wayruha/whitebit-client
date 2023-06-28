package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderSide {
  BUY("buy"), SELL("sell");

  private final String name;

  OrderSide(String name) {
    this.name = name;
  }

  @JsonValue
  public String getName() {
    return name;
  }
}
