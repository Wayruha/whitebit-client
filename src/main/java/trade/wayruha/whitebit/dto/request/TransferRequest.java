package trade.wayruha.whitebit.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import trade.wayruha.whitebit.domain.enums.Account;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

@Data
@AllArgsConstructor
public class TransferRequest {
  private Account from;
  private Account to;
  private String ticker;
  @JsonIgnore
  private BigDecimal qty;

  /**
   * Server requires to send amount as String. Though, we would like to work with numbers
   */
  public String getAmount() {
    if (isNull(qty)) return null;
    return qty.toPlainString();
  }
}

