package trade.wayruha.whitebit.client.helper;

import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientLogger implements HttpLoggingInterceptor.Logger {
  private final Logger logger;

  public HttpClientLogger(Class forClass) {
    this.logger = LoggerFactory.getLogger(forClass);
  }

  public HttpClientLogger() {
    this(HttpClientLogger.class);
  }

  @Override
  public void log(@NotNull String msg) {
    logger.debug(msg);
  }
}
