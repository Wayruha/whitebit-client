package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class AvailableTicker {
    @JsonAlias("base_id")
    private int baseId;
    @JsonAlias("quote_id")
    private int quoteId;
    @JsonAlias("last_price")
    private String lastPrice;
    @JsonAlias("quote_volume")
    private String quoteVolume;
    @JsonAlias("base_volume")
    private String baseVolume;
    private boolean isFrozen;
    private String change;
}
