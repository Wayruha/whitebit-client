package trade.wayruha.whitebit.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class BulkOrdersCreation {
  List<NewOrderRequest> orders;
  boolean stopOnFail;
}
