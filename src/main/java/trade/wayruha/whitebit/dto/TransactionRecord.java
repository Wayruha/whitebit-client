package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import trade.wayruha.whitebit.domain.enums.TransactionMethod;

import java.math.BigDecimal;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRecord {
  private String address;
  private String uniqueId;
  private long createdAt;
  private String currency;
  private String ticker;
  private TransactionMethod method;
  private BigDecimal amount;
  private String description;
  private String memo;
  private BigDecimal fee;
  private Integer status;
  private String network;
  private String transactionHash;
  private String transactionId;
  private WithdrawalDetails details;
  private WithdrawalConfirmations confirmations;
  private Boolean centralized;

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class WithdrawalDetails {
    private BigDecimal requestAmount;
    private BigDecimal processedAmount;
    private BigDecimal processedFee;
    private String normalizeTransaction;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class WithdrawalDetailsWrapper {
    private WithdrawalDetails partial;
  }

  @Data
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class WithdrawalConfirmations {
    private BigDecimal actual;
    private BigDecimal required;
  }
}
