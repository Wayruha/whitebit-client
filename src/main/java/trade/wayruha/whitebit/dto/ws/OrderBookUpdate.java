package trade.wayruha.whitebit.dto.ws;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.OrderBook;
import trade.wayruha.whitebit.utils.ModelParser;
import trade.wayruha.whitebit.exception.ModelParserException;

@Data
public class OrderBookUpdate {
  private boolean fullSnapshot;
  private OrderBook orderBook;

  public static class Parser extends ModelParser<OrderBookUpdate> {
    private final JavaType orderBookType;

    public Parser(ObjectMapper mapper) {
      super(mapper);
      this.orderBookType = mapper.constructType(OrderBook.class);
    }

    @Override
    public OrderBookUpdate parseUpdate(ArrayNode updateParams) throws ModelParserException {
      try {
        if (updateParams.size() < 3) throw new IllegalArgumentException("At least 3 elements expected");
        final OrderBookUpdate dto = new OrderBookUpdate();
        final ObjectNode orderBookNode = (ObjectNode) updateParams.get(1);
        final OrderBook orderBook = mapper.convertValue(orderBookNode, orderBookType);
        final String marketStr = updateParams.get(2).asText();
        dto.setFullSnapshot(updateParams.get(0).asBoolean());
        dto.setOrderBook(orderBook);
        orderBook.setTickerId(Market.parse(marketStr));
        return dto;
      } catch (Exception ex) {
        throw new ModelParserException(updateParams, ex);
      }
    }
  }
}
