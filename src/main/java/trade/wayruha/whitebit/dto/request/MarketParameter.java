package trade.wayruha.whitebit.dto.request;

import lombok.Data;
import trade.wayruha.whitebit.domain.Market;

@Data
public class MarketParameter {
  private final Market market;

  public MarketParameter() {
    this.market = null;
  }

  public MarketParameter(Market market) {
    this.market = market;
  }
}
