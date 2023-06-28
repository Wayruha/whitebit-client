package trade.wayruha.whitebit.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import trade.wayruha.whitebit.domain.OrderSide;
import trade.wayruha.whitebit.domain.OrderType;

import java.math.BigDecimal;

@Data
public class OrderResponse {
  private Long orderId;
  private String clientOrderId;
  private String market;
  private OrderSide side;
  private OrderType type;
  private BigDecimal price;
  private BigDecimal amount;
  @JsonAlias("dealStock")
  private BigDecimal filledQty;
  @JsonAlias("dealMoney")
  private BigDecimal filledTotal;
  @JsonAlias("takerFee")
  private BigDecimal takerFeeRatio;
  @JsonAlias("makerFee")
  private BigDecimal makerFeeRatio;
  @JsonAlias("left")
  private BigDecimal amountLeft;
  @JsonAlias("dealFee")
  private BigDecimal quoteFee;
  private boolean postOnly;
  private boolean ioc;
  private long timestamp;
}
