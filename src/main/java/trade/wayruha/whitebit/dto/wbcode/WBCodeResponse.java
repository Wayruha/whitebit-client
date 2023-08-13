package trade.wayruha.whitebit.dto.wbcode;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WBCodeResponse {
  private String code;
  private String message;
  @JsonAlias("external_id")
  private String externalId;
  private BigDecimal amount;
}
