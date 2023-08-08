package trade.wayruha.whitebit.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.enums.OrderSide;
import trade.wayruha.whitebit.domain.enums.OrderType;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewOrderRequest {
  private Market market;
  private OrderSide side;
  private BigDecimal price;
  private String clientOrderId;
  private Boolean postOnly;
  private Boolean ioc;

  /** amount refers to base asset for LIMIT BUY/SELL and MARKET SELL. For MARKET BUY orders, amount refers to quote asset   */
  private BigDecimal amount;
  @JsonIgnore
  private OrderType orderType;

  public static NewOrderRequest marketOrderBaseAsset(Market market, OrderSide side, BigDecimal baseQty, String clientOrderId) {
    return NewOrderRequest.builder()
        .orderType(OrderType.MARKET_STOCK).market(market).side(side).amount(baseQty).clientOrderId(clientOrderId)
        .build();
  }

  public static NewOrderRequest marketOrder(Market market, OrderSide side, BigDecimal amount, String clientOrderId) {
    return NewOrderRequest.builder()
        .orderType(OrderType.MARKET).market(market).side(side).amount(amount).clientOrderId(clientOrderId)
        .build();
  }

  public static NewOrderRequest limitOrder(Market market, OrderSide side, BigDecimal baseQty, BigDecimal price,
                                              String clientOrderId, Boolean postOnly, Boolean ioc) {
    return NewOrderRequest.builder()
        .orderType(OrderType.LIMIT).market(market).side(side).amount(baseQty).price(price).clientOrderId(clientOrderId)
        .postOnly(postOnly).ioc(ioc)
        .build();
  }

  public static NewOrderRequest limitOrder(Market market, OrderSide side, BigDecimal baseQty, BigDecimal price) {
    return limitOrder(market, side, baseQty, price, null, null, null);
  }

  public static NewOrderRequest marginLimitOrder(Market market, OrderSide side, BigDecimal baseQty, BigDecimal price) {
    final NewOrderRequest req = limitOrder(market, side, baseQty, price, null, null, null);
    req.setOrderType(OrderType.MARGIN_LIMIT);
    return req;
  }

  public static NewOrderRequest marginMarketOrder(Market market, OrderSide side, BigDecimal baseQty, String clientOrderId) {
    final NewOrderRequest req = marketOrder(market, side, baseQty, clientOrderId);
    req.setOrderType(OrderType.MARGIN_MARKET);
    return req;
  }
}

