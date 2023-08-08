package trade.wayruha.whitebit.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CollateralSummary {
  private BigDecimal equity;  // total equity of collateral balance including lending funds in USDT
  private BigDecimal margin;  // amount of funds in open position USDT
  private BigDecimal freeMargin; // free funds for trading according to
  private BigDecimal unrealizedFunding;  // funding that will be paid on next position stage change (order, liquidation, etc)
  private BigDecimal pnl; // curren profit and loss in USDT
  private int leverage; // current leverage of account which affect amount of lending funds
  private BigDecimal marginFraction; // margin fraction
  private BigDecimal maintenanceMarginFraction; // maintenance margin fraction
}