package trade.wayruha.whitebit.dto.request;

import lombok.Data;

@Data
public class TickerParam {
  private String ticker;

  public TickerParam(String ticker) {
    this.ticker = ticker;
  }
}
