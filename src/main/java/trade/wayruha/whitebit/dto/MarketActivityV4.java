package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

//TODO should we remove completely MarketActivity v1 and v2?
@Data
public class MarketActivityV4 {
  @JsonAlias("base_id")
  private int baseId;
  @JsonAlias("quote_id")
  private int quoteId;
  @JsonAlias("last_price")
  private BigDecimal lastPrice;
  @JsonAlias("quote_volume")
  private BigDecimal quoteVolume;
  @JsonAlias("base_volume")
  private BigDecimal baseVolume;
  private boolean isFrozen;
  private BigDecimal change;
}
