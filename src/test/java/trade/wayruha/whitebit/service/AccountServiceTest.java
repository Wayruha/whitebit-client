package trade.wayruha.whitebit.service;

import org.junit.Test;
import trade.wayruha.whitebit.TestConstants;
import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.domain.enums.TransactionMethod;
import trade.wayruha.whitebit.dto.DepositAddressResponse;
import trade.wayruha.whitebit.dto.PageableResponse;
import trade.wayruha.whitebit.dto.TransactionFees;
import trade.wayruha.whitebit.dto.TransactionRecord;
import trade.wayruha.whitebit.dto.request.TransactionHistoryRequest;
import trade.wayruha.whitebit.dto.request.TransferRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
import static trade.wayruha.whitebit.domain.enums.Account.MAIN;
import static trade.wayruha.whitebit.domain.enums.Account.TRADE;

public class AccountServiceTest {
  WBConfig config = TestConstants.getSimpleConfig();
  AccountService service = new AccountService(config);
  final String usdt = "USDT";

  @Test
  public void test_getDepositAddress() {
    final DepositAddressResponse address = service.getDepositAddress(usdt, "TRC20");
    System.out.println("Address for " + usdt + ": " + address);
  }

  @Test
  public void test_transfers() {
    final BigDecimal qty = new BigDecimal("1");
    final TransferRequest mainToTrade = new TransferRequest(MAIN, TRADE, usdt, qty);
    final TransferRequest tradeToMain = new TransferRequest(TRADE, MAIN, usdt, qty);
    service.transfer(mainToTrade);
    System.out.println("Transferred " + qty + " " + usdt + " from main to trade");
    service.transfer(tradeToMain);
    System.out.println("Transferred " + qty + " " + usdt + " from trade to main");
  }

  @Test
  public void test_getTransactionHistory_Deposit() {
    final TransactionHistoryRequest req = new TransactionHistoryRequest();
    req.setTransactionMethod(TransactionMethod.DEPOSIT);
    final PageableResponse<TransactionRecord> transactionHistory = service.getTransactionHistory(req);
    System.out.println("TransactionHistory: " + transactionHistory);
    transactionHistory.getData().forEach(record -> {
      assertEquals(TransactionMethod.DEPOSIT, record.getMethod());
      assertTrue(record.getCreatedAt() > 0);
      assertNotNull(record.getCurrency());
      assertNotNull(record.getTicker());
      assertNotNull(record.getTransactionId());
      assertNotNull(record.getAmount());
    });
  }

  @Test
  public void test_getTransactionHistoryWithdraw() {
    final TransactionHistoryRequest req = new TransactionHistoryRequest();
    req.setTransactionMethod(TransactionMethod.WITHDRAW);
    final PageableResponse<TransactionRecord> transactionHistory = service.getTransactionHistory(req);
    System.out.println("TransactionHistory: " + transactionHistory);
    transactionHistory.getData().forEach(record -> {
      assertEquals(TransactionMethod.WITHDRAW, record.getMethod());
      assertTrue(record.getCreatedAt() > 0);
      assertNotNull(record.getCurrency());
      assertNotNull(record.getTicker());
      assertNotNull(record.getTransactionId());
      assertNotNull(record.getAmount());
    });
  }

  @Test
  public void test_getFeesStructure() {
    final List<TransactionFees> response = service.getFeesStructure();
    System.out.println("Fees: " + response);
    response.forEach(r -> {
      assertNotNull(r.getName());
      assertNotNull(r.getTicker());
      assertNotNull(r.getCanDeposit());
      assertNotNull(r.getCanWithdraw());
      assertNotNull(r.getDeposit());
      assertNotNull(r.getWithdraw());
    });
  }
}
