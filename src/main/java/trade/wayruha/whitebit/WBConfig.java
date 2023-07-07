package trade.wayruha.whitebit;

import lombok.Data;

import static trade.wayruha.whitebit.APIConstant.HTTP_CLIENT_TIMEOUT_MS;

@Data
public class WBConfig {
  public static final String DEFAULT_HTTP_HOST = "https://whitebit.com/";
  public static final String DEFAULT_WS_HOST = "wss://api.whitebit.com/ws";

  private String apiKey;
  private String apiSecret;
  private String host = DEFAULT_HTTP_HOST;
  private String webSocketHost = DEFAULT_WS_HOST;

  /** Host connection timeout. */
  private long httpConnectTimeout = HTTP_CLIENT_TIMEOUT_MS;
  /** The host reads the information timeout.*/
  private long httpReadTimeout = HTTP_CLIENT_TIMEOUT_MS;
  /** The host writes the information timeout. */
  private long httpWriteTimeout = HTTP_CLIENT_TIMEOUT_MS;
  /** Retry on failed connection, default true. */
  private boolean retryOnConnectionFailure = true;
  /** Should we log request's data?*/
  private boolean httpLogRequestData = false;

  /** WebSocket should try re-connecting on fail forever */
  private boolean webSocketReconnectAlways = false;
  /** If not forever, then how many times? */
  private int webSocketMaxReconnectAttempts = 3;
  private int webSocketPingIntervalSec = 45;

  public WBConfig(String apiKey, String apiSecret) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
  }
}
