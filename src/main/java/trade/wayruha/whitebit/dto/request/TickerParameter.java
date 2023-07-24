package trade.wayruha.whitebit.dto.request;

import lombok.Data;

@Data
public class TickerParameter {
  private final String ticker;

  public TickerParameter() {
    this.ticker = null;
  }

  public TickerParameter(String ticker) {
    this.ticker = ticker;
  }
}
