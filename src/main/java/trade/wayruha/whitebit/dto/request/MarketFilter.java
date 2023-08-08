package trade.wayruha.whitebit.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import trade.wayruha.whitebit.domain.Market;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MarketFilter extends Pageable {
  private final Market market;

  public MarketFilter(Market market) {
    super(null, null);
    this.market = market;
  }

  public MarketFilter(Market market, Integer limit, Integer offset) {
    super(limit, offset);
    this.market = market;
  }
}
