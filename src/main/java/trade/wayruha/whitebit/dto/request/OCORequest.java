package trade.wayruha.whitebit.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OCORequest extends NewOrderRequest {
  @JsonProperty("activation_price")
  private BigDecimal activationPrice;
  @JsonProperty("stop_limit_price")
  private BigDecimal stopLimitPrice;
}
