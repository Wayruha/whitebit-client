package trade.wayruha.whitebit.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Arrays;

public enum OrderType {

  LIMIT(1, "limit"),
  MARKET(2, "market"),
  MARKET_STOCK(202, "stock market"),
  STOP_LIMIT(3, "stop limit"),
  STOP_MARKET(4, "stop market"),
  MARGIN_LIMIT(7, "margin limit"),
  MARGIN_MARKET(8, "margin market"),
  MARGIN_STOP_LIMIT(9, "margin stop limit"),
  MARGIN_STOP_MARKET(10, "trigger margin market"),
  MARGIN_NORMALIZATION(14, "normalization");

  private final int code;
  private final String name;

  OrderType(int code, String name) {
    this.code = code;
    this.name = name;
  }

  OrderType(int code) {
    this.code = code;
    this.name = null;
  }

  @JsonValue
  public String getName() {
    return name;
  }

  public static OrderType fromCode(int code) {
    return Arrays.stream(values())
        .filter(type -> code == type.code)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown OrderType code:" + code));
  }

  public static OrderType fromName(String name) {
    return Arrays.stream(values())
        .filter(type -> name.equalsIgnoreCase(type.name))
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown OrderType name:" + name));
  }

  public static class Deserializer extends JsonDeserializer<OrderType> {
    @Override
    public OrderType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
      final JsonNode node = p.getCodec().readTree(p);
      if (node.isNull()) return null;
      if (node.isNumber()) return fromCode(node.asInt());
      else return fromName(node.asText());
    }
  }
}
