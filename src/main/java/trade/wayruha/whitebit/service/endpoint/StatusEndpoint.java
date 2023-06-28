package trade.wayruha.whitebit.service.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface StatusEndpoint {
  @GET("/api/v4/public/ping")
  Call<List<String>> getExchangeInfo();
}
