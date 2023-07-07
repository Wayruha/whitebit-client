package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Value;
import trade.wayruha.whitebit.domain.Market;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderBook {
  @JsonAlias("ticker_id")
  private Market tickerId;
  private long timestamp;
  private List<Level> asks;
  private List<Level> bids;

  //TODO add methods to apply partial updates

  @Value
  @JsonDeserialize(using = Level.LevelDeserializer.class)
  public static class Level {
    BigDecimal price;
    BigDecimal amount;

    public Level(BigDecimal price, BigDecimal amount) {
      this.price = price;
      this.amount = amount;
    }

    public static class LevelDeserializer extends JsonDeserializer<Level> {
      @Override
      public Level deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        final BigDecimal price = new BigDecimal(node.get(0).asText());
        final BigDecimal quantity = new BigDecimal(node.get(1).asText());
        ;
        return new Level(price, quantity);
      }
    }
  }
}
