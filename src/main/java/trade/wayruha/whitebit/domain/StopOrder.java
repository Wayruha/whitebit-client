package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import trade.wayruha.whitebit.domain.enums.ActivationCondition;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StopOrder extends Order {
  @JsonAlias({"activation_price"})
  private BigDecimal activationPrice;

  @JsonAlias({"activation_condition"})
  private ActivationCondition activationCondition;
}
