package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.enums.ProductType;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class FuturesMarket {
  @JsonAlias("ticker_id")
  private Market ticker;
  @JsonAlias("stock_currency")
  private String baseCurrency;
  @JsonAlias("money_currency")
  private String quoteCurrency;
  @JsonAlias("last_price")
  private BigDecimal lastPrice;
  @JsonAlias("stock_volume")
  private BigDecimal baseAssetVolume;
  @JsonAlias("money_volume")
  private BigDecimal quoteAssetVolume;
  @JsonAlias("bid")
  private BigDecimal highestBid;
  @JsonAlias("ask")
  private BigDecimal lowestAsk;
  @JsonAlias("high")
  private BigDecimal highest24HrsPrice;
  @JsonAlias("low")
  private BigDecimal lowest24HrsPrice;
  @JsonAlias("product_type")
  private ProductType productType;
  @JsonAlias("open_interest")
  private BigDecimal openInterest;
  @JsonAlias("index_price")
  private BigDecimal indexPrice;
  @JsonAlias("index_name")
  private String indexName;
  @JsonAlias("index_currency")
  private String indexCurrency;
  @JsonAlias("funding_rate")
  private BigDecimal fundingRate;
  @JsonAlias("next_funding_rate_timestamp")
  private long nextFundingRateTimestamp;
  @JsonAlias("max_leverage")
  private int maxLeverage;
  private Map<Integer, Integer> brackets;
}
