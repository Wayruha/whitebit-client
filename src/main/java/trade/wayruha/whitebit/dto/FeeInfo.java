package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeeInfo {
  @JsonAlias("is_depositable")
  private boolean isDepositable;
  @JsonAlias("is_withdrawal")
  private boolean isWithdrawal;
  private String ticker;
  private String name;
  private List<String> providers;
  private Fee withdraw;
  private Fee deposit;

  @Data
  public static class Fee {
      @JsonAlias("max_amount")
      private BigDecimal maxAmount;
      @JsonAlias("min_amount")
      private BigDecimal minAmount;
      private BigDecimal fixed;
      private FlexibleFee flex;

      @Data
      public static class FlexibleFee {
          private BigDecimal percent;
          private BigDecimal min_fee;
          private BigDecimal max_fee;
      }
  }
}