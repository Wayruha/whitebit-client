package trade.wayruha.whitebit.client.helper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import trade.wayruha.whitebit.WBConfig;

import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class HttpClientBuilder {
  private final WBConfig config;
  private final HttpLoggingInterceptor loggingInterceptor;

  public HttpClientBuilder(final WBConfig config) {
    this.config = config;
    final HttpLoggingInterceptor logInterceptor = getDefaultLoggingInterceptor();
    this.loggingInterceptor = logInterceptor;
  }

  public HttpClientBuilder(final WBConfig config, HttpLoggingInterceptor loggingInterceptor) {
    this.config = config;
    this.loggingInterceptor = loggingInterceptor;
  }

  public OkHttpClient buildClient() {
    final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    clientBuilder.connectTimeout(this.config.getHttpConnectTimeout(), TimeUnit.MILLISECONDS)
        .readTimeout(this.config.getHttpReadTimeout(), TimeUnit.MILLISECONDS)
        .writeTimeout(this.config.getHttpWriteTimeout(), TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(this.config.isRetryOnConnectionFailure());
    if (this.config.isHttpLogRequestData() && loggingInterceptor != null) {
      clientBuilder.addInterceptor(loggingInterceptor);
    }
    if (isNotEmpty(config.getApiKey())) {
      final AuthenticationInterceptor signatureInterceptor = new AuthenticationInterceptor(config.getApiKey(), config.getApiSecret());
      clientBuilder.addInterceptor(signatureInterceptor);
    }
    return clientBuilder.build();
  }

  @NotNull
  private static HttpLoggingInterceptor getDefaultLoggingInterceptor() {
    final HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpClientLogger());
    logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return logInterceptor;
  }
}
