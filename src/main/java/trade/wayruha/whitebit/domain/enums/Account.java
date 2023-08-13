package trade.wayruha.whitebit.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Account {
  MAIN("main"),
  TRADE("spot"),
  COLLATERAL("collateral");

  Account(String name) {
    this.name = name;
  }

  private final String name;

  @JsonValue
  public String getName() {
    return name;
  }
}
