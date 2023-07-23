package trade.wayruha.whitebit.domain;


import java.util.Arrays;

public enum OrderSide {
  SELL("sell", 1),
  BUY("buy", 2);

  private final String name;
  private final int code;

  OrderSide(String name, int id) {
    this.name = name;
    this.code = id;
  }

  public static OrderSide fromCode(int code) {
    return Arrays.stream(values())
        .filter(side -> side.code == code)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown OrderSide code:" + code));
  }

  public static OrderSide fromName(String name) {
    return Arrays.stream(values())
        .filter(side -> side.name.equalsIgnoreCase(name))
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown OrderSide name:" + name));
  }
}
