package trade.wayruha.whitebit.ws;

import lombok.ToString;

@ToString(callSuper = true)
public class Subscription extends WSRequest {
  protected Subscription(String method, Object... params) {
    super(method, params);
  }
}
