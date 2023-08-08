package trade.wayruha.whitebit.dto.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Builder;
import lombok.Data;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.enums.OrderSide;
import trade.wayruha.whitebit.exception.ModelParserException;
import trade.wayruha.whitebit.utils.ModelParser;

import java.math.BigDecimal;

import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;

@Data
@Builder
public class DealsUpdate {
  private long dealId;
  private double dealTime;
  private Market market;
  private long orderId;
  private BigDecimal price;
  private BigDecimal amount;
  private BigDecimal dealFeeQuote;
  private String clientOrderId;
  private OrderSide side;

  public static class Parser extends ModelParser<DealsUpdate> {

    public Parser(ObjectMapper mapper) {
      super(mapper, DealsUpdate.class);
    }

    @Override
    public DealsUpdate parseUpdate(ArrayNode updateParams) throws ModelParserException {
      try {
        final long dealId = updateParams.get(0).asLong();
        final double dealTime = updateParams.get(1).asDouble();
        final Market market = Market.parse(updateParams.get(2).asText());
        final long orderId = updateParams.get(3).asLong();
        final BigDecimal price = createBigDecimal(updateParams.get(4).asText());
        final BigDecimal amount = createBigDecimal(updateParams.get(5).asText());
        final BigDecimal fee = createBigDecimal(updateParams.get(6).asText());
        final String clientOrderId = updateParams.get(7).asText();
        final OrderSide side = OrderSide.fromCode(updateParams.get(8).asInt());
        return DealsUpdate.builder()
            .dealId(dealId)
            .orderId(orderId)
            .dealTime(dealTime)
            .market(market)
            .price(price)
            .amount(amount)
            .dealFeeQuote(fee)
            .clientOrderId(clientOrderId)
            .side(side).build();
      } catch (Exception ex) {
        throw new ModelParserException(updateParams, ex);
      }
    }
  }
}
