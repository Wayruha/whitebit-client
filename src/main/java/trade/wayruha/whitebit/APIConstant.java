package trade.wayruha.whitebit;

import java.util.List;
import java.util.Set;

public class APIConstant {
  /**
   * The default timeout is 30 seconds.
   */
  public static final long HTTP_CLIENT_TIMEOUT_MS = 30 * 1000;

  /**
   * max allowed discrepancy in nonce (i.e., timestamp, millis)
   */
  public static final int DEFAULT_RECEIVING_WINDOW = 60_000;
  public static final String WEBSOCKET_INTERRUPTED_EXCEPTION = "The server terminated the connection for an unknown reason";
  public static final String API_CLIENT_ERROR_MESSAGE_PARSE_EXCEPTION = "Can't parse error message";
  public static final String ORDER_NOT_FOUND_ERROR_MSG = "OrderHistory was not found";

  public static final String ENDPOINT_SECURITY_SIGNED = "API_SIGNED";
  public static final String ENDPOINT_SECURITY_SIGNED_HEADER = ENDPOINT_SECURITY_SIGNED + ": #";

  public static final String HEADER_ACCESS_KEY = "X-TXC-APIKEY";
  public static final String HEADER_PAYLOAD = "X-TXC-PAYLOAD";
  public static final String HEADER_SIGNATURE = "X-TXC-SIGNATURE";
  public static final String HEADER_CONTENT_TYPE = "Content-type";
  public static final String CONTENT_TYPE_JSON = "application/json";

  public static final String LISTEN_KEY_QUERY_PARAM = "listenKey";

  public static final String CRYPTO_HMAC_SHA512 = "HmacSHA512";

  public static final int MAX_WS_BATCH_SUBSCRIPTIONS = 1000;
  public static final int FETCH_ORDERS_MAX_LIMIT = 100;
  public static final int FETCH_ORDERS_MAX_OFFSET = 10000;

  public static final Set<Integer> MARGIN_LEVERAGE_ACCEPTABLE_VALUES = Set.of(1, 2, 3, 5, 10, 20, 50, 100);
}
