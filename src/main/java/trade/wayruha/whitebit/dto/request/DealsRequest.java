package trade.wayruha.whitebit.dto.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import trade.wayruha.whitebit.domain.Market;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DealsRequest extends MarketFilter {
  String clientOrderId;

  @Builder
  public DealsRequest(String clientOrderId, Market market, Integer limit, Integer offset) {
    super(market, limit, offset);
    this.clientOrderId = clientOrderId;
  }

  public static DealsRequest all() {
    return DealsRequest.builder().build();
  }

  public static DealsRequest byMarket(Market market) {
    return DealsRequest.builder().market(market).build();
  }

  public static DealsRequest byClientOrderId(String clientOrderId) {
    return DealsRequest.builder().clientOrderId(clientOrderId).build();
  }
}
