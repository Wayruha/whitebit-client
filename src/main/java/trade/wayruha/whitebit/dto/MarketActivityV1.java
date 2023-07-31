package trade.wayruha.whitebit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MarketActivityV1 {
    private Ticker ticker;
    private Long at;

    @Data
    public static class Ticker {
      private BigDecimal open;
      private BigDecimal bid;
      private BigDecimal ask;
      private BigDecimal low;
      private BigDecimal high;
      private BigDecimal last;
      private BigDecimal volume;
      private BigDecimal deal;
      private BigDecimal change;
    }
}