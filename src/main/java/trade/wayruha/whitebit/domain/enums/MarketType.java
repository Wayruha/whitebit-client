package trade.wayruha.whitebit.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MarketType {
  SPOT("spot"),
  FUTURES("futures");

  private final String name;

  MarketType(String name) {
    this.name = name;
  }

  @JsonValue
  public String getName() {
    return name;
  }
}
