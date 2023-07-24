package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import trade.wayruha.whitebit.domain.enums.ActivationCondition;

public class OCOOrder {
  long id;
  @JsonAlias("stop_loss")
  private Order stopLoss;
  @JsonAlias("take_profit")
  private Order takeProfit;

  public static class Order extends trade.wayruha.whitebit.domain.Order {
    private ActivationCondition activationCondition;
    private boolean activated;

    public void setActivated(int activated){
      this.activated = activated == 1;
    }
  }
}
