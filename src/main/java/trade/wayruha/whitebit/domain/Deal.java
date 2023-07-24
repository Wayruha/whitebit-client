package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import trade.wayruha.whitebit.domain.enums.TraderRole;

import java.math.BigDecimal;

@Data
public class Deal {
  private long id;
  @JsonAlias({"dealOrderId", "orderId"})
  private long dealOrderId;
  private String clientOrderId;
  private BigDecimal amount;
  @JsonAlias("deal")
  private BigDecimal total;
  private BigDecimal price;
  private TraderRole role;
  private BigDecimal fee;
  private Double time;
}
