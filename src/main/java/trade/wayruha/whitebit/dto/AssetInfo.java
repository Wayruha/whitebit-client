package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class AssetInfo {
  private String name;
  @JsonAlias("unified_cryptoasset_id")
  private long unifiedCryptoassetId;
  @JsonAlias("can_withdraw")
  private boolean canWithdraw;
  @JsonAlias("can_deposit")
  private boolean canDeposit;
  @JsonAlias("min_withdraw")
  private BigDecimal minWithdraw;
  @JsonAlias("max_withdraw")
  private BigDecimal maxWithdraw;
  @JsonAlias("maker_fee")
  private BigDecimal makerFee;
  @JsonAlias("taker_fee")
  private BigDecimal takerFee;
  @JsonAlias("min_deposit")
  private BigDecimal minDeposit;
  @JsonAlias("max_deposit")
  private BigDecimal maxDeposit;
  private Networks networks;
  private Map<String, Integer> confirmations;
  private Limits limits;
  @JsonAlias("currency_precision")
  private long currencyPrecision;
  @JsonAlias("is_memo")
  private boolean isMemo;

  @Data
  public static class Networks {
    private List<String> deposits;
    private List<String> withdraws;
    @JsonAlias("default")
    private String defaultNetwork;
  }

  @Data
  public static class Confirmations {
    private Map<String, Integer> ERC20;
  }

  @Data
  public static class Limits {
    private Map<String, NetworkLimits> deposit;
    private Map<String, NetworkLimits> withdraw;
  }

  @Data
  public static class NetworkLimits {
    private BigDecimal min;
  }
}