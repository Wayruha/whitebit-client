package trade.wayruha.whitebit.dto;

import lombok.Data;
import trade.wayruha.whitebit.domain.DepositAddress;

@Data
public class DepositAddressResponse {
  private DepositAddress account;
  private NetworkFee required;
}
