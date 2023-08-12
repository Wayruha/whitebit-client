package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.dto.Asset;
import trade.wayruha.whitebit.dto.Fee;
import trade.wayruha.whitebit.dto.Trade;
import trade.wayruha.whitebit.service.endpoint.PublicEndpoints;

import java.util.List;
import java.util.Map;

public class PublicService extends ServiceBase{

    private final PublicEndpoints api;
    public PublicService(WBConfig config) {
        super(config);

        this.api = createService(PublicEndpoints.class);
    }

    Map<String, Fee> getFees(){
        return client.executeSync(api.getFees()).getData();
    }

    public Map<String, Asset> getAssets() {
        return client.executeSync(api.getAssets()).getData();
    }

    List<Trade> getTrades(Market symbol, String type) {
        return client.executeSync(api.getTrades(symbol, type)).getData();
    }
}
