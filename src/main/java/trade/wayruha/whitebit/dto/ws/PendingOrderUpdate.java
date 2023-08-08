package trade.wayruha.whitebit.dto.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
import trade.wayruha.whitebit.domain.enums.OrderEventType;
import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.exception.ModelParserException;
import trade.wayruha.whitebit.utils.ModelParser;

@Data
public class PendingOrderUpdate {
  private OrderEventType updateType;
  private Order order;

  public static class Parser extends ModelParser<PendingOrderUpdate> {
    public Parser(ObjectMapper mapper) {
      super(mapper);
    }

    @Override
    public PendingOrderUpdate parseUpdate(ArrayNode updateParams) throws ModelParserException {
      try {
        final int updateType = updateParams.get(0).asInt();
        final Order order = mapper.convertValue(updateParams.get(1), Order.class);
        final PendingOrderUpdate dto = new PendingOrderUpdate();
        dto.setUpdateType(OrderEventType.fromCode(updateType));
        dto.setOrder(order);
        return dto;
      } catch (Exception ex) {
        throw new ModelParserException(updateParams, ex);
      }
    }
  }
}
