package trade.wayruha.whitebit.dto.request;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Data
public class WithdrawFiatRequest {
  private String ticker;
  private BigDecimal amount;
  private String address;
  private String memo;
  private String uniqueId;
  private String provider;
  private Boolean partialEnable;
  private Beneficiary beneficiary;

  @Value
  public static class Beneficiary {
    String firstName;
    String lastName;
    Integer tin; //required for ticker = UAH_IBAN
    String phone;
  }
}
