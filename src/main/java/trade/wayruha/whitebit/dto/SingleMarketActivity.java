package trade.wayruha.whitebit.dto;

import lombok.Data;

@Data
public class SingleMarketActivity {
    private String open;
    private String bid;
    private String ask;
    private String low;
    private String high;
    private String last;
    private String volume;
    private String deal;
    private String change;
}

