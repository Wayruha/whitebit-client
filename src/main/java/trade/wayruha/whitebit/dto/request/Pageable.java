package trade.wayruha.whitebit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pageable {
  protected Integer limit;
  protected Integer offset;

  protected Pageable() {
  }

  public static Pageable all(){
    return new Pageable();
  }
}
