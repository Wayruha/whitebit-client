package trade.wayruha.whitebit.domain;

import lombok.Value;

@Value
public class OrderID {
  Market market;
  Long orderId;
}
