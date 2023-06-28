package trade.wayruha.whitebit.dto.request;

import lombok.Data;
import trade.wayruha.whitebit.domain.OrderSide;

import java.math.BigDecimal;

@Data
public class OrderRequest {
  private String market;
  private OrderSide side;
  private BigDecimal amount;
  private BigDecimal price;
  private String clientOrderId;
  private boolean postOnly;
  private boolean ioc;
}
