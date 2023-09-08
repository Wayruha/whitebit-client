package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.enums.MarketType;

import java.math.BigDecimal;

@Data
public class MarketInfo {
  private Market name;
  @JsonAlias({"stock"})
  private String baseAsset;
  @JsonAlias({"money"})
  private String quoteAsset;
  @JsonAlias({"stockPrec"})
  private Integer basePrecision;
  @JsonAlias({"moneyPrec"})
  private Integer quotePrecision;
  private BigDecimal makerFee;
  private BigDecimal takerFee;
  private BigDecimal minAmount;
  private BigDecimal minTotal;
  private BigDecimal maxTotal;
  private boolean tradesEnabled;
  private boolean isCollateral;
  private MarketType type;
}
