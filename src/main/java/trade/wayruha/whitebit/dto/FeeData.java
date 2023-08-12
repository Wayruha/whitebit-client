package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeeData {
    @JsonAlias("max_amount")
    private BigDecimal maxAmount;
    @JsonAlias("min_amount")
    private BigDecimal minAmount;
    private BigDecimal fixed;
    private FlexFee flex;
}
