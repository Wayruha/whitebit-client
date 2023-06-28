package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import trade.wayruha.whitebit.Main;

@Data
public class WBRequest<T> {
  private String request;
  @JsonUnwrapped
  private T requestParams;
  private Long nonce;
  private boolean nonceWindow;

  public WBRequest(String request, T requestParams, Long nonce, boolean nonceWindow) {
    this.request = request;
    this.nonce = nonce;
    this.nonceWindow = nonceWindow;
    this.requestParams = requestParams;
  }

  public static <T> WBRequest<T> request(String url, T params) {
    return new WBRequest<>(url, params, Main.getCurrentTime(), true);
  }
}
