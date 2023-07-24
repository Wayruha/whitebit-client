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
  @JsonIgnore
  private OrderType orderType;
  @JsonIgnore
  private BigDecimal baseQty;
  @JsonIgnore
  private BigDecimal quoteQty;

  /** amount refers to base asset for LIMIT BUY/SELL and MARKET SELL. For MARKET BUY orders, amount refers to quote asset   */
  public BigDecimal getAmount() {
    return nonNull(baseQty) ? baseQty : quoteQty;
  }

  public static NewOrderRequest marketOrderBaseAsset(Market market, OrderSide side, BigDecimal baseQty, String clientOrderId) {
    return NewOrderRequest.builder()
        .orderType(OrderType.MARKET).market(market).side(side).baseQty(baseQty).clientOrderId(clientOrderId)
        .build();
  }

  public static NewOrderRequest marketOrderQuoteAsset(Market market, OrderSide side, BigDecimal quoteQty, String clientOrderId) {
    return NewOrderRequest.builder()
        .orderType(OrderType.MARKET).market(market).side(side).quoteQty(quoteQty).clientOrderId(clientOrderId)
        .build();
  }

  public static NewOrderRequest limitOrder(Market market, OrderSide side, BigDecimal baseQty, BigDecimal price,
                                              String clientOrderId, Boolean postOnly, Boolean ioc) {
    return NewOrderRequest.builder()
        .orderType(OrderType.LIMIT).market(market).side(side).baseQty(baseQty).price(price).clientOrderId(clientOrderId)
        .postOnly(postOnly).ioc(ioc)
        .build();
  }

  public static NewOrderRequest limitOrder(Market market, OrderSide side, BigDecimal baseQty, BigDecimal price) {
    return limitOrder(market, side, baseQty, price, null, null, null);
  }
}

