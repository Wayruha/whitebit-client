package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import trade.wayruha.whitebit.domain.enums.OrderSide;
import trade.wayruha.whitebit.domain.enums.OrderType;
import trade.wayruha.whitebit.domain.enums.TraderRole;

import java.math.BigDecimal;

@Data
public class Order {
  @JsonAlias({"id", "orderId"})
  private Long orderId;
  @JsonAlias({"client_order_id", "clientOrderId"})
  private String clientOrderId;
  private Market market;
  @JsonDeserialize(using = OrderSide.Deserializer.class)
  private OrderSide side;
  private OrderType type;
  private BigDecimal price;
  private BigDecimal amount;
  @JsonAlias({"dealStock", "deal_stock"})
  private BigDecimal filledQty;
  @JsonAlias({"dealMoney", "deal_money", "deal"})
  private BigDecimal filledTotal;
  @JsonAlias({"takerFee", "taker_fee"})
  private BigDecimal takerFeeRatio; // taker fee ratio. If the number less than 0.0001 - it will be rounded to zero
  @JsonAlias({"makerFee", "maker_fee"})
  private BigDecimal makerFeeRatio; // maker fee ratio. If the number less than 0.0001 - it will be rounded to zero
  @JsonAlias("left")
  private BigDecimal amountLeft;
  @JsonAlias({"dealFee", "deal_fee", "fee"})
  private BigDecimal quoteFee;
  @JsonAlias({"postOnly", "post_only"})
  private Boolean postOnly;  // orders are guaranteed to be the maker order when executed.
  private Boolean ioc;
  private TraderRole role;
  @JsonAlias({"timestamp", "ctime"})
  private Double timestamp;
  @JsonAlias("mtime")
  private Double modifiedTime;
  @JsonAlias("ftime")
  private Double finishTime;
}
