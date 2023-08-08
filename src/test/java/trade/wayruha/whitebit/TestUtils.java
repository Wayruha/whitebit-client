package trade.wayruha.whitebit;

import org.apache.commons.lang3.StringUtils;
import trade.wayruha.whitebit.domain.Deal;
import trade.wayruha.whitebit.domain.MarginPosition;
import trade.wayruha.whitebit.domain.Order;
import trade.wayruha.whitebit.dto.request.NewOrderRequest;

import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class TestUtils {
  public static void validateDeal(Deal deal) {
    assertTrue(deal.getId() > 0);
    assertTrue(deal.getDealOrderId() > 0);
    assertTrue(deal.getTime() > 0);
    assertNotNull(deal.getPrice());
    assertNotNull(deal.getRole());
    assertNotNull(deal.getAmount());
    assertNotNull(deal.getTotal());
    assertNotNull(deal.getFee());
  }

  public static void validateOrder(Order order) {
    assertNotNull(order);
    assertNotNull(order.getOrderId());
    assertNotNull(order.getMarket());
    assertNotNull(order.getSide());
    assertNotNull(order.getPrice());
    assertNotNull(order.getAmount());
    assertNotNull(order.getFilledQty());
    assertNotNull(order.getFilledTotal());
    assertNotNull(order.getQuoteFee());
    assertNotNull(order.getTakerFeeRatio());
    assertNotNull(order.getMakerFeeRatio());
    assertTrue(order.getTimestamp() > 0);
  }

  public static void compareOrderWithRequested(NewOrderRequest orderRequest, Order actual) {
    assertNotNull(actual);
    assertNotNull(actual.getOrderId());
    assertEquals(orderRequest.getSide(), actual.getSide());
    assertEquals(ofNullable(orderRequest.getPrice()).orElse(BigDecimal.ZERO), ofNullable(actual.getPrice()).orElse(BigDecimal.ZERO));
    assertNotNull(actual.getAmountLeft());
    assertNotNull(actual.getFilledQty());
    assertNotNull(actual.getFilledTotal());
    assertNotNull(actual.getQuoteFee());
    assertNotNull(actual.getTakerFeeRatio());
    assertNotNull(actual.getMakerFeeRatio());
    assertEquals(Boolean.TRUE.equals(orderRequest.getPostOnly()), actual.getPostOnly());
    assertEquals(Boolean.TRUE.equals(orderRequest.getIoc()), actual.getIoc());
    assertTrue(Objects.equals(orderRequest.getClientOrderId(), actual.getClientOrderId()) || StringUtils.isBlank(actual.getClientOrderId()));
    assertNotNull(actual.getTimestamp());
  }

  public static void validateOpenPosition(MarginPosition position) {
    assertNotNull(position);
    assertNotNull(position.getMarket());
    assertNotNull(position.getMargin());
    assertNotNull(position.getFreeMargin());
    assertNotNull(position.getFunding());
    assertNotNull(position.getUnrealizedFunding());
    assertTrue(position.getPositionId() >0 );
    assertNotNull(position.getModifyDate());
    assertNotNull(position.getOpenDate());
  }

  public static void validateClosedPosition(MarginPosition position){
    assertNotNull(position);
    assertNotNull(position.getMarket());
    assertTrue(position.getPositionId() > 0);
    assertNotNull(position.getRealizedFunding());
    assertNotNull(position.getOrderDetail());
    assertNotNull(position.getOrderDetail().getBasePrice());
    assertNotNull(position.getOrderDetail().getTradeAmount());
    assertNotNull(position.getOrderDetail().getRealizedPnl());
    assertNotNull(position.getOrderDetail().getFundingFee());
    assertNotNull(position.getOrderDetail().getTradeFee());
    assertTrue(position.getOrderDetail().getId()>0);
    assertNotNull(position.getModifyDate());
    assertNotNull(position.getOpenDate());
  }

  public static void validateOpenExecutedPosition(MarginPosition position) {
    assertNotNull(position.getAmount());
    assertNotNull(position.getBasePrice());
    assertNotNull(position.getLiquidationPrice());
    assertNotNull(position.getPnl());
    assertNotNull(position.getPnl());
  }
}
