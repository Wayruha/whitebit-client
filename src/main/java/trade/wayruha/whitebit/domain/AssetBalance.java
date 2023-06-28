package trade.wayruha.whitebit.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetBalance {
  private String asset;
  private BigDecimal available;
  private BigDecimal freeze;
}
