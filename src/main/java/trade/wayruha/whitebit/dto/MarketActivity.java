package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import trade.wayruha.whitebit.domain.OrderSide;
import trade.wayruha.whitebit.domain.OrderType;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MarketActivity {
  private String lastUpdateTimestamp;
  private String tradingPairs;
  private String lastPrice;
  private String lowestAsk;
  private String highestBid;
  private String baseVolume24h;
  private String quoteVolume24h;
  private boolean tradesEnabled;
}

