package trade.wayruha.whitebit.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class Asset {
    private String name;
    private BigDecimal unified_cryptoasset_id;
    private boolean can_withdraw;
    private boolean can_deposit;
    private String min_withdraw;
    private String max_withdraw;
    private String maker_fee;
    private String taker_fee;
    private String min_deposit;
    private String max_deposit;
    private Networks networks;
    private Confirmations confirmations;
    private Limits limits;
    private BigDecimal currency_precision;
    private boolean is_memo;

    @Data
    public static class Networks {
        private List<String> deposits;
        private List<String> withdraws;
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
        private String min;
    }
}