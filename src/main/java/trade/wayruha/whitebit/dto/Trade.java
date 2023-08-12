package trade.wayruha.whitebit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Trade {
    private BigDecimal tradeID;
    private String price;
    private String quote_volume;
    private String base_volume;
    private BigDecimal trade_timestamp;
    private String type;
}
