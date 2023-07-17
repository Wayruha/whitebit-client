package trade.wayruha.whitebit.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import trade.wayruha.whitebit.client.ApiClient;
import trade.wayruha.whitebit.exception.InvalidAPIKeys;
import trade.wayruha.whitebit.service.AccountService;
import trade.wayruha.whitebit.utils.ModelParser;

import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class WebSocketPrivateClient<T> extends WebSocketSubscriptionClient<T> {
  private final AccountService accountService;

  public WebSocketPrivateClient(ApiClient apiClient, ObjectMapper mapper, WebSocketCallback<T> callback, ModelParser<T> modelParser) {
    super(apiClient, mapper, callback, modelParser);
    if (isBlank(config.getApiKey()) || isBlank(config.getApiSecret()))
      throw new InvalidAPIKeys("API keys must be specified");
    this.accountService = new AccountService(apiClient);
  }

  @SneakyThrows
  @Override
  protected void connect(Set<Subscription> subscriptions) {
    super.connect(Set.of());
    authorize();
    Thread.sleep(2_000);
    subscribe(subscriptions);
  }

  private void authorize() {
    final String webSocketToken = accountService.getWebSocketToken();
    final WSRequest req = new WSRequest("authorize", webSocketToken, "public");
    sendRequest(req);
  }
}
