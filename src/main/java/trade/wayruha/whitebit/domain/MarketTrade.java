package trade.wayruha.whitebit.domain;

import lombok.Data;
import trade.wayruha.whitebit.domain.enums.OrderSide;

import java.math.BigDecimal;

@Data
public class MarketTrade {
  private long id;
  private Double timestamp;
  private BigDecimal price;
  private BigDecimal amount;
  private OrderSide tradeType;

  public void setType(String typeName){
    this.tradeType = OrderSide.fromName(typeName);
  }
}
