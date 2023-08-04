package trade.wayruha.whitebit.dto.request;

import lombok.Value;
import trade.wayruha.whitebit.domain.Market;

@Value
public class PositionsRequest {
  Market market;
  Long positionId;

  public static PositionsRequest byId(Long positionId) {
    return new PositionsRequest(null, positionId);
  }

  public static PositionsRequest byMarket(Market market) {
    return new PositionsRequest(market, null);
  }
}
