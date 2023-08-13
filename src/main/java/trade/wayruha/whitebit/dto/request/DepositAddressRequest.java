package trade.wayruha.whitebit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DepositAddressRequest {
  String ticker;
  String network;
  String type; // only for address creation request;

  public DepositAddressRequest(String ticker, String network) {
    this.ticker = ticker;
    this.network = network;
    this.type = null;
  }
}
