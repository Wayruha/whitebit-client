package trade.wayruha.whitebit.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.enums.OrderSide;
import trade.wayruha.whitebit.domain.enums.OrderType;

import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StopOrderRequest extends NewOrderRequest {
  @JsonProperty("activation_price")
  protected BigDecimal activationPrice;

  /**
   * amount refers to base asset for LIMIT BUY/SELL and MARKET SELL. For MARKET BUY orders, amount refers to quote asset
   * <a href="https://whitebit-exchange.github.io/api-docs/private/http-trade-v4/#create-stop-market-order">link</a>
   */
  public static StopOrderRequest spotStopMarketOrder(Market market, OrderSide side, BigDecimal amount, BigDecimal activationPrice, String clientOrderId) {
    final StopOrderRequest req = new StopOrderRequest();
    req.setMarket(market);
    req.setSide(side);
    req.setActivationPrice(activationPrice);
    req.setClientOrderId(clientOrderId);
    req.setAmount(amount);
    req.setOrderType(OrderType.STOP_MARKET);
    return req;
  }

  /**
   * Margin StopMarket orders are created based on Base Asset
   * <a href="https://whitebit-exchange.github.io/api-docs/private/http-trade-v4/#collateral-market-order">link</a>
   */
  public static StopOrderRequest marginStopMarketOrder(Market market, OrderSide side, BigDecimal baseQty, BigDecimal activationPrice, String clientOrderId) {
    final StopOrderRequest req = spotStopMarketOrder(market, side, baseQty, activationPrice, clientOrderId);
    req.setOrderType(OrderType.MARGIN_STOP_MARKET);
    return req;
  }

  public static StopOrderRequest spotStopLimitOrder(Market market, OrderSide side, BigDecimal baseQty, BigDecimal triggerPrice,
                                                    BigDecimal activationPrice, String clientOrderId) {
    final StopOrderRequest req = new StopOrderRequest();
    req.setMarket(market);
    req.setSide(side);
    req.setActivationPrice(activationPrice);
    req.setPrice(triggerPrice);
    req.setClientOrderId(clientOrderId);
    req.setAmount(baseQty);
    req.setOrderType(OrderType.STOP_LIMIT);
    return req;
  }

  public static StopOrderRequest marginStopLimitOrder(Market market, OrderSide side, BigDecimal baseQty, BigDecimal triggerPrice,
                                                      BigDecimal activationPrice, String clientOrderId) {
    final StopOrderRequest req = spotStopLimitOrder(market, side, baseQty, triggerPrice, activationPrice, clientOrderId);
    req.setOrderType(OrderType.MARGIN_STOP_LIMIT);
    return req;
  }
}
