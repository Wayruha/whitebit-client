package trade.wayruha.whitebit.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LiquidationState {
  MARGIN_CALL("margin_call"),
  LIQUIDATION("liquidation");

  private final String name;

  LiquidationState(String name) {
    this.name = name;
  }

  @JsonValue
  public String getName() {
    return name;
  }
}
