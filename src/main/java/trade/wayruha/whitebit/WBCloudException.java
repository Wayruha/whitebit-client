package trade.wayruha.whitebit;

import java.util.List;
import java.util.Map;

public class WBCloudException extends RuntimeException {
  private Integer code;
  private String message;
  private boolean success;
  private Map<String, List<String>> errors;
  private List params;

  public WBCloudException(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public WBCloudException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }

  @Override
  public String toString() {
    return "WBCloudException{" + "code=" + code + ", message='" + message + "\'}";
  }
}
