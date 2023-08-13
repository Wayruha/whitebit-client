package trade.wayruha.whitebit.dto;

import lombok.Data;
import trade.wayruha.whitebit.domain.DepositAddress;

import java.math.BigDecimal;

@Data
public class DepositAddressResponse {
  private DepositAddress account;
  private Meta required;

  @Data
  public static class Meta {
    private BigDecimal fixedFee;
    private BigDecimal maxAmount;
    private BigDecimal minAmount;
    private FeeRatio flexFee;

    //Flexible fee is a fee based on amount
    @Data
    public static class FeeRatio {
      private BigDecimal maxFee;
      private BigDecimal minFee;
      private BigDecimal percent;
    }
  }
}
