package trade.wayruha.whitebit.exception;

import java.util.List;
import java.util.Map;

public class WBCloudException extends RuntimeException {
  private Integer code;
  private String errorMessage;
  private boolean success;
  private Map<String, List<String>> errors;
  private List params;

  public WBCloudException(int code, String message) {
    this.code = code;
    this.errorMessage = message;
  }

  public WBCloudException(String message, Throwable cause) {
    super(message, cause);
    this.errorMessage = message;
  }

  @Override
  public String getMessage() {
    return errorMessage;
  }

  @Override
  public String toString() {
    return "WBCloudException{" + "code=" + code + ", message='" + errorMessage + "\'}";
  }
}
