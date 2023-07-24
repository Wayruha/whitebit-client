package trade.wayruha.whitebit.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActivationCondition {
  GTE("gte");

  private final String name;

  ActivationCondition(String name) {
    this.name = name;
  }

  @JsonValue
  public String getName() {
    return name;
  }
}
