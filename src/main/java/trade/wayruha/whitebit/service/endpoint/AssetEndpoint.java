package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import trade.wayruha.whitebit.dto.Asset;

import java.util.Map;

public interface AssetEndpoint {
    @GET("/api/v4/public/assets")
    Call<Map<String, Asset>> getAssets();
}
