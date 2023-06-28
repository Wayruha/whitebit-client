package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.client.APIConstant;
import trade.wayruha.whitebit.client.ApiClient;

public abstract class ServiceBase {
  protected int receivingWindow = APIConstant.DEFAULT_RECEIVING_WINDOW;
  protected final ApiClient client;

  public ServiceBase(ApiClient client) {
    this.client = client;
  }

  public ServiceBase(WBConfig config) {
    this(new ApiClient(config));
  }

  protected <T> T createService(Class<T> apiClass) {
    return client.createService(apiClass);
  }

  protected long now() {
    return System.currentTimeMillis();
  }
}
