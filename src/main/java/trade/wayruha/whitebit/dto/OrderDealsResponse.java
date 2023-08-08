package trade.wayruha.whitebit.dto;

import lombok.Data;
import trade.wayruha.whitebit.domain.Deal;

import java.util.List;

@Data
public class OrderDealsResponse {
  private List<Deal> records;
  private Integer offset;
  private Integer limit;
}
