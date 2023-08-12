package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.dto.Asset;
import trade.wayruha.whitebit.service.endpoint.AssetEndpoint;
import trade.wayruha.whitebit.service.endpoint.TickerEndpoint;

import java.util.Map;

public class AssetService extends ServiceBase {
    private final AssetEndpoint api;

    public AssetService(WBConfig config) {
        super(config);
        this.api = createService(AssetEndpoint.class);
    }

    public Map<String, Asset> getAssets() {
         return client.executeSync(api.getAssets()).getData();
    }
}
