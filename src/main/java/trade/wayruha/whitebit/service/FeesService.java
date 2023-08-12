package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.Fee;
import trade.wayruha.whitebit.service.endpoint.FeeEndpoint;

import java.util.Map;

public class FeesService extends ServiceBase{
    private final FeeEndpoint api;

    public FeesService(WBConfig config) {
        super(config);
        this.api = createService(FeeEndpoint.class);
    }

    Map<String, Fee> getFees(){
        return client.executeSync(api.getFees()).getData();
    }
}
