package trade.wayruha.whitebit.dto.wbcode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WBCodeRecord extends WBCodeResponse {
  private long date;
  private String status;
  private String ticker;
}
