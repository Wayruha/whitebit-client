package trade.wayruha.whitebit.dto.request;

import lombok.Value;

import static trade.wayruha.whitebit.APIConstant.MARGIN_LEVERAGE_ACCEPTABLE_VALUES;

@Value
public class ChangeLeverageRequest {
  int leverage;

  public ChangeLeverageRequest(int leverage) {
    //TODO move to service layer
    if(!MARGIN_LEVERAGE_ACCEPTABLE_VALUES.contains(leverage)) throw new IllegalArgumentException(leverage + " is not acceptable leverage.");
    this.leverage = leverage;
  }
}
