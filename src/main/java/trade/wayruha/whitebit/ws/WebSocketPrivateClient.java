package trade.wayruha.whitebit.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import trade.wayruha.whitebit.client.ApiClient;
import trade.wayruha.whitebit.utils.ModelParser;
import trade.wayruha.whitebit.service.AccountService;

import java.util.Set;

public class WebSocketPrivateClient<T> extends WebSocketSubscriptionClient<T> {
  private final AccountService accountService;

  public WebSocketPrivateClient(ApiClient apiClient, ObjectMapper mapper, WebSocketCallback<T> callback, ModelParser<T> modelParser) {
    super(apiClient, mapper, callback, modelParser);
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
