package trade.wayruha.whitebit.dto;

import lombok.Data;

@Data
public class SingleTicker {
    private SingleMarketActivity ticker;
    private Long at;
}