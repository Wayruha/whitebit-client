package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum OrderType {
  LIMIT("limit"), MARKET("market");
  //TODO replace content with OrderTypeId

  private final String name;

  OrderType(String name) {
    this.name = name;
  }

  @JsonValue
  public String getName() {
    return name;
  }

  public static OrderType fromCode(int code){
    return null; //todo implement
  }

  public static OrderType fromName(String name){
    return Arrays.stream(values())
        .filter(type->type.name.equalsIgnoreCase(name))
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown OrderType name:" + name));
  }
}
