package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlexibleFee {
    private BigDecimal percent;
    private BigDecimal min_fee;
    private BigDecimal max_fee;
}
