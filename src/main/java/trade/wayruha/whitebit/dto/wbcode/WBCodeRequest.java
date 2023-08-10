package trade.wayruha.whitebit.dto.wbcode;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WBCodeRequest {
  private String ticker;
  private BigDecimal amount;
  private String passphrase;
  private String description;
}
