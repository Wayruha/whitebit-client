package trade.wayruha.whitebit.client;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WBResponse<T> {
  private final int httpStatus;
  private final T data;

  public WBResponse(int httpStatus, T data) {
    this.httpStatus = httpStatus;
    this.data = data;
  }
}
