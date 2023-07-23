package trade.wayruha.whitebit.dto.request;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ExecutedOrdersFilter {
  LIMIT_AND_MARKET(0),
  LIMIT(1),
  MARKET(2);

  @JsonValue
  @Getter
  private final int code;

  ExecutedOrdersFilter(int code) {
    this.code = code;
  }
}
