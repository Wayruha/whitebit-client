package trade.wayruha.whitebit.dto;

import lombok.Data;
import trade.wayruha.whitebit.domain.Market;

import java.math.BigDecimal;

@Data
public class MarketActivity {
  private String lastUpdateTimestamp;
  private Market tradingPairs;
  private BigDecimal lastPrice;
  private BigDecimal lowestAsk;
  private BigDecimal highestBid;
  private BigDecimal baseVolume24h;
  private BigDecimal quoteVolume24h;
  private boolean tradesEnabled;
}

