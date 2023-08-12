package trade.wayruha.whitebit.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ProductType {
  FUTURES("Futures"),
  PERPETUAL("Perpetual"),
  OPTIONS("Options");

  @JsonValue
  @Getter
  private final String name;

  ProductType(String name) {
    this.name = name;
  }
}
