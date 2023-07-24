package trade.wayruha.whitebit.dto.request;

import lombok.Builder;
import lombok.Value;
import trade.wayruha.whitebit.domain.Market;

@Value
@Builder
public class PositionsRequest {
  Market market;
  long positionId;

  public static PositionsRequest byId(Long orderId) {
    return PositionsRequest.builder().positionId(orderId).build();
  }

  public static PositionsRequest byMarket(Market market) {
    return PositionsRequest.builder().market(market).build();
  }
}
