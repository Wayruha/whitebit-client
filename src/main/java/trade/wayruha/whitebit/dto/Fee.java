package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fee {
    @JsonAlias("is_depositable")
    private boolean isDepositable;
    @JsonAlias("is_withdrawal")
    private boolean isWithdrawal;
    private String ticker;
    private String name;
    private String[] providers;
    private Withdraw withdraw;
    private Deposit deposit;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Withdraw {
        private String max_amount;
        private String min_amount;
        private String fixed;
        private Flex flex;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Deposit {
        private String max_amount;
        private String min_amount;
        private String fixed;
        private Flex flex;
    }

    @Data
    public static class Flex {
        private String percent;
        private String min_fee;
        private String max_fee;
    }
}
