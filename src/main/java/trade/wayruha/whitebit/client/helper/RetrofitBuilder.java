package trade.wayruha.whitebit.client.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.ClientConfig;

public class RetrofitBuilder {

  public static Retrofit buildRetrofit(WBConfig configuration, OkHttpClient httpClient) {
    final ObjectMapper objectMapper = ClientConfig.getObjectMapper();

    Retrofit.Builder builder = new Retrofit.Builder();
    builder.client(httpClient);
    builder.addConverterFactory(JacksonConverterFactory.create(objectMapper));
    builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    builder.baseUrl(configuration.getHost());
    return builder.build();
  }
}
