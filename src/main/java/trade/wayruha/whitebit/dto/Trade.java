package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import trade.wayruha.whitebit.domain.enums.OrderSide;

import java.math.BigDecimal;

@Data
public class Trade {
    private long tradeID;
    private BigDecimal price;
    @JsonAlias("quote_volume")
    private BigDecimal quoteVolume;
    @JsonAlias("base_volume")
    private BigDecimal baseVolume;
    @JsonAlias("trade_timestamp")
    private long tradeTimestamp;
    private OrderSide type;
}
