package trade.wayruha.whitebit.domain.enums;


import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
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

  @JsonValue
  public String getName() {
    return name;
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

  public static class Deserializer extends JsonDeserializer<OrderSide> {
    @Override
    public OrderSide deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
      final JsonNode node = p.getCodec().readTree(p);
      if (node.isNull()) return null;
      if (node.isNumber()) return fromCode(node.asInt());
      else return fromName(node.asText());
    }
  }
}
