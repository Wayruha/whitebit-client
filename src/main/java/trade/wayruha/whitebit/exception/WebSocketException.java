package trade.wayruha.whitebit.exception;

import trade.wayruha.whitebit.ws.WSResponse;

import static java.util.Objects.nonNull;

public class WebSocketException extends RuntimeException {
  private WSResponse.Error error;
  private String status;

  public WebSocketException(String message) {
    super(message);
  }

  public WebSocketException(String message, WSResponse.Error error, String status) {
    super(message);
    this.error = error;
    this.status = status;
  }

  @Override
  public String toString() {
    String str = super.toString();
    if (nonNull(error)) str += "; error=" + error;
    if (nonNull(str)) str += "; status=" + status;
    return str;
  }
}
