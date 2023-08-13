package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionFees {
  private String ticker;
  private String name;
  @JsonAlias("can_deposit")
  private Boolean canDeposit;
  @JsonAlias("can_withdraw")
  private Boolean canWithdraw;
  private WireSetting deposit;
  private WireSetting withdraw;

  @Data
  public static class WireSetting {
    private BigDecimal minFlex;
    private BigDecimal maxFlex;
    private BigDecimal percentFlex;
    private BigDecimal fixed;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
  }
}
