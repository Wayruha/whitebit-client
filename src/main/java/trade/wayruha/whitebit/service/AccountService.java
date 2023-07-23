package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.client.ApiClient;
import trade.wayruha.whitebit.service.endpoint.AccountEndpoint;

public class AccountService extends ServiceBase {
  private final AccountEndpoint api;

  public AccountService(WBConfig config) {
    super(config);
    this.api = createService(AccountEndpoint.class);
  }

  public AccountService(ApiClient client) {
    super(client);
    this.api = createService(AccountEndpoint.class);
  }

  public String getWebSocketToken(){
    return client.executeSync(api.getWebSocketToken()).getData().getToken();
  }
}
