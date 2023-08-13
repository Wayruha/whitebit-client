package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class NetworkFee {
  @JsonAlias({"max_amount", "maxAmount"})
  private BigDecimal maxAmount;
  @JsonAlias({"min_amount", "minAmount"})
  private BigDecimal minAmount;
  @JsonAlias({"fixedFee", "fixed"})
  private BigDecimal fixed;
  @JsonAlias({"flexFee", "flex"})
  private FlexibleFee flex;

  @Data
  public static class FlexibleFee {
    private BigDecimal percent;
    @JsonAlias({"min_fee", "minFee"})
    private BigDecimal minFee;
    @JsonAlias({"max_fee", "maxFee"})
    private BigDecimal maxFee;
  }
}
