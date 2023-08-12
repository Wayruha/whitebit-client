package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.Trade;
import trade.wayruha.whitebit.service.endpoint.TradesEndpoint;

import java.util.List;

public class TradesService extends ServiceBase{
    private final TradesEndpoint api;

    public TradesService(WBConfig config) {
        super(config);
        this.api = createService(TradesEndpoint.class);
    }

    List<Trade> getTrades(Market symbol, String type) {
        return client.executeSync(api.getTrades(symbol, type)).getData();
    }
}
