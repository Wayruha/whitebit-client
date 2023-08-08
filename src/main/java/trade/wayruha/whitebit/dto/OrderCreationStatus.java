package trade.wayruha.whitebit.dto;

import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.exception.WBCloudException;

import java.util.List;

public class OrderCreationStatus {
  private Order result;
  private List<WBCloudException> error;
}
