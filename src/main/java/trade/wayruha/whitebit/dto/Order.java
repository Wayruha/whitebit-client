package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.OrderSide;
import trade.wayruha.whitebit.domain.OrderType;

import java.math.BigDecimal;

@Data
public class Order {
  @JsonAlias({"id", "orderId"})
  private Long orderId;
  @JsonAlias({"client_order_id", "clientOrderId"})
  private String clientOrderId;
  private Market market;
  private OrderSide side;
  private OrderType type;
  private BigDecimal price;
  private BigDecimal amount;
  @JsonAlias({"dealStock", "deal_stock"})
  private BigDecimal filledQty;
  @JsonAlias({"dealMoney", "deal_money"})
  private BigDecimal filledTotal;
  @JsonAlias("takerFee")
  private BigDecimal takerFeeRatio;
  @JsonAlias("makerFee")
  private BigDecimal makerFeeRatio;
  @JsonAlias("left")
  private BigDecimal amountLeft;
  @JsonAlias({"dealFee", "deal_fee"})
  private BigDecimal quoteFee;
  @JsonAlias({"postOnly", "post_only"})
  private Boolean postOnly;
  private Boolean ioc;
  @JsonAlias({"timestamp", "ctime"})
  private long timestamp;
  @JsonAlias("mtime")
  private Float modifiedTime;
  @JsonAlias("ftime")
  private Float finishTime;

  public void setSide(String sideName) {
    this.side = OrderSide.fromName(sideName);
  }

  public void setSide(int sideCode) {
    this.side = OrderSide.fromCode(sideCode);
  }

  public void setType(String typeName) {
    this.type = OrderType.fromName(typeName);
  }

  public void setType(int typeCode) {
    this.type = OrderType.fromCode(typeCode);
  }
}
