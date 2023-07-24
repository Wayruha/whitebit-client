package trade.wayruha.whitebit.domain;

import lombok.Data;
import trade.wayruha.whitebit.domain.enums.LiquidationState;

import java.math.BigDecimal;

/**
 * NOTE: In case of position opening using trigger or limit order you can get situation
 *  when basePrice, liquidationPrice, amount, pnl, pnlPercent returns with null value.
 * It happens when funds are lending, and you start to pay funding fee, but position is not completely opened, cos activation price hadn't been triggered yet.
 */
@Data
public class MarginPosition {
  private long positionId;
  private Market market;
  private BigDecimal amount;
  private BigDecimal basePrice;
  private BigDecimal liquidationPrice;
  private LiquidationState liquidationState;
  private int leverage; // current collateral balance leverage
  private BigDecimal pnl; // current profit and loss in quoteAsset
  private BigDecimal pnlPercent;
  private BigDecimal margin; // amount of funds in open position quoteAsset
  private BigDecimal freeMargin; // free funds for trading according to
  private BigDecimal funding; // funding that will be paid on next position stage change (order, liquidation, etc)
  private BigDecimal unrealizedFunding; // funding that will be paid on next position stage change (order, liquidation, etc)
  private Double openDate;
  private Double modifyDate;
  private Order orderDetails; // details of order which changes position

  public static class Order {
    private long id;
    private BigDecimal tradeAmount;
    private BigDecimal basePrice;
    private BigDecimal tradeFee;
    private BigDecimal fundingFee; // funding fee which was captured by this position change (order)
    private BigDecimal realizedPnl;
  }
}
