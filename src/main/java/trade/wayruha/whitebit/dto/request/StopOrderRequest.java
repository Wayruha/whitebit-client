package trade.wayruha.whitebit.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.enums.OrderSide;

import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StopOrderRequest extends NewOrderRequest {
  @JsonProperty("activation_price")
  protected BigDecimal activationPrice;

  /**
   * amount refers to base asset for LIMIT BUY/SELL and MARKET SELL. For MARKET BUY orders, amount refers to quote asset
   */
  public static StopOrderRequest stopMarketOrder(Market market, OrderSide side, BigDecimal amount, BigDecimal activationPrice, String clientOrderId) {
    final StopOrderRequest req = new StopOrderRequest();
    req.setMarket(market);
    req.setSide(side);
    req.setActivationPrice(activationPrice);
    req.setClientOrderId(clientOrderId);
    if (side == OrderSide.BUY) {
      req.setQuoteQty(amount);
    } else {
      req.setBaseQty(amount);
    }
    return req;
  }

  public static StopOrderRequest stopLimitOrder(Market market, OrderSide side, BigDecimal baseQty, BigDecimal triggerPrice,
                                                BigDecimal activationPrice, String clientOrderId) {
    final StopOrderRequest req = new StopOrderRequest();
    req.setMarket(market);
    req.setSide(side);
    req.setActivationPrice(activationPrice);
    req.setPrice(triggerPrice);
    req.setClientOrderId(clientOrderId);
    req.setBaseQty(baseQty);
    return req;
  }
}
