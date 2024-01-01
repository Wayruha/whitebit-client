package trade.wayruha.whitebit.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import trade.wayruha.whitebit.utils.BigDecimalSerializer;

import java.math.BigDecimal;

@Data
public class WithdrawRequest {
  private String ticker;
  @JsonSerialize(using = BigDecimalSerializer.class)
  private BigDecimal amount;
  private String address;
  private String memo;
  private String uniqueId;
  private String network;
}
