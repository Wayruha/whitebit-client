package trade.wayruha.whitebit.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawRequest {
  private String ticker;
  private BigDecimal amount;
  private String address;
  private String memo;
  private String uniqueId;
  private String network;
}
