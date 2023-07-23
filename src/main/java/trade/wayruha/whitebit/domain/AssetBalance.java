package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetBalance {
  protected String asset;
  @JsonAlias({"available", "balance", "B"})
  protected BigDecimal available;
  protected BigDecimal freeze;
}
