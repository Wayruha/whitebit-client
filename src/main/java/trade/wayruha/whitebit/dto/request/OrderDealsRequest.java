package trade.wayruha.whitebit.dto.request;

import lombok.Builder;
import lombok.Value;

@Value
public class OrderDealsRequest extends Pageable {
  Long orderId;

  public OrderDealsRequest(Long orderId) {
    super(null, null);
    this.orderId = orderId;
  }

  @Builder
  public OrderDealsRequest(Long orderId, Integer limit, Integer offset) {
    super(limit, offset);
    this.orderId = orderId;
  }
}
