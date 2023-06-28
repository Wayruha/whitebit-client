package trade.wayruha.whitebit;

import lombok.Data;
import trade.wayruha.whitebit.client.APIConstant;

@Data
public class WBConfig {
  public static final String DEFAULT_HTTP_HOST = "https://whitebit.com/";
  public static final String DEFAULT_WS_HOST = "wss://wbs.mexc.com/ws";

  private String apiKey;
  private String apiSecret;
  private String host = DEFAULT_HTTP_HOST;
  private String webSocketHost = DEFAULT_WS_HOST;

  /**
   * Host connection timeout.
   */
  private long connectTimeout;
  /**
   * The host reads the information timeout.
   */
  private long readTimeout;
  /**
   * The host writes the information timeout.
   */
  private long writeTimeout;
  /**
   * Failure reconnection, default true.
   */
  private boolean retryOnConnectionFailure;

  /**
   * Should we log request's data?
   */
  private boolean print;

  public WBConfig(String apiKey, String apiSecret) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
    this.readTimeout = APIConstant.TIMEOUT;
    this.writeTimeout = APIConstant.TIMEOUT;
    this.retryOnConnectionFailure = true;
    this.print = false;
  }
}
