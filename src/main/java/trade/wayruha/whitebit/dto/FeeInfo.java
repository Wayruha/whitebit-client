package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeeInfo {
  @JsonAlias("is_depositable")
  private boolean isDepositable;
  @JsonAlias("is_withdrawal")
  private boolean isWithdrawal;
  private String ticker;
  private String name;
  private List<String> providers;
  private NetworkFee withdraw;
  private NetworkFee deposit;
}