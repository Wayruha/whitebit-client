package trade.wayruha.whitebit.domain.enums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.Arrays;

@JsonDeserialize(using = TraderRole.Deserializer.class)
public enum TraderRole {
  MAKER(1),
  TAKER(2);

  private final int code;

  TraderRole(int code) {
    this.code = code;
  }

  public static TraderRole fromCode(int code) {
    return Arrays.stream(values())
        .filter(role -> role.code == code)
        .findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown TradeRole code: " + code));
  }

  public static class Deserializer extends JsonDeserializer<TraderRole> {
    @Override
    public TraderRole deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      final JsonNode node = p.getCodec().readTree(p);
      if (node.isNull()) return null;
      return fromCode(node.asInt());
    }
  }
}
