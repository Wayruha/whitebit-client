package trade.wayruha.whitebit.dto.request;

import lombok.Value;
import trade.wayruha.whitebit.domain.Market;

@Value
public class KillSwitchRequest {
  Market market;
  Long timeout;
}
