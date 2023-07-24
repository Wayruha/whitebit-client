package trade.wayruha.whitebit.dto.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import trade.wayruha.whitebit.domain.Market;


@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderDetailsRequest extends Pageable {
  Market market;
  Long orderId;
  String clientOrderId;

  @Builder
  public OrderDetailsRequest(Market market, Long orderId, String clientOrderId, Integer limit, Integer offset) {
    super(limit, offset);
    this.market = market;
    this.orderId = orderId;
    this.clientOrderId = clientOrderId;
  }

  public static OrderDetailsRequest all() {
    return new OrderDetailsRequest(null, null, null, null, null);
  }

  public static OrderDetailsRequest byMarket(Market market) {
    return OrderDetailsRequest.builder().market(market).build();
  }

  public static OrderDetailsRequest byId(Market market, Long orderId) {
    return OrderDetailsRequest.builder().market(market).orderId(orderId).build();
  }

  public static OrderDetailsRequest byClientOrderId(Market market, String clientOrderId) {
    return OrderDetailsRequest.builder().market(market).clientOrderId(clientOrderId).build();
  }
}
