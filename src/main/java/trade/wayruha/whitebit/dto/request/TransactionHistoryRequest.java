package trade.wayruha.whitebit.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import trade.wayruha.whitebit.domain.enums.DepositStatus;
import trade.wayruha.whitebit.domain.enums.TransactionMethod;
import trade.wayruha.whitebit.domain.enums.WithdrawalStatus;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransactionHistoryRequest extends Pageable {
  private TransactionMethod transactionMethod;
  private String ticker;
  private List<String> addresses;
  private String uniqueId;
  private List<Integer> status;

  public TransactionHistoryRequest() {
  }

  public TransactionHistoryRequest(Integer limit, Integer offset) {
    super(limit, offset);
  }

  public void setStatus(List<Integer> statusCodes){
    this.status = statusCodes;
  }

  public void setStatus(WithdrawalStatus status){
    setStatus(status.getStatusCodes());
  }

  public void setStatus(DepositStatus status){
    setStatus(status.getStatusCodes());
  }
}
