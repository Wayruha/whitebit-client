package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fee {
    @JsonAlias("is_depositable")
    private boolean isDepositable;
    @JsonAlias("is_withdrawal")
    private boolean isWithdrawal;
    private String ticker;
    private String name;
    private List<String> providers;
    private FeeData withdraw;
    private FeeData deposit;
}