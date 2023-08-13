package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.WBConfig;
import trade.wayruha.whitebit.client.ApiClient;
import trade.wayruha.whitebit.dto.*;
import trade.wayruha.whitebit.dto.request.*;
import trade.wayruha.whitebit.service.endpoint.AccountEndpoint;
import trade.wayruha.whitebit.service.endpoint.WalletEndpoint;

import java.util.List;

public class AccountService extends ServiceBase {
  private final AccountEndpoint accountApi;
  private final WalletEndpoint walletApi;

  public AccountService(ApiClient client) {
    super(client);
    this.accountApi = createService(AccountEndpoint.class);
    this.walletApi = createService(WalletEndpoint.class);
  }

  public AccountService(WBConfig config) {
    super(config);
    this.accountApi = createService(AccountEndpoint.class);
    this.walletApi = createService(WalletEndpoint.class);
  }

  public DepositAddressResponse getDepositAddress(String asset, String network) {
    final DepositAddressRequest req = new DepositAddressRequest(asset, network);
    return client.executeSync(accountApi.getDepositAddress(req)).getData();
  }

  /**
   * Not supported by default. Contact support@whitebit.com
   */
  public FiatDepositAddress getFiatDepositAddress(FiatDepositAddressRequest req) {
    return client.executeSync(accountApi.getFiatDepositAddress(req)).getData();
  }

  public void transfer(TransferRequest request) {
    client.executeSync(walletApi.universalTransfer(request));
  }

  public PageableResponse<TransactionRecord> getTransactionHistory(TransactionHistoryRequest request){
    return client.executeSync(accountApi.getWalletHistory(request)).getData();
  }

  public void withdrawCrypto(WithdrawRequest request, boolean feeIncluded){
    if(feeIncluded){
      client.executeSync(accountApi.withdraw(request));
    } else {
      client.executeSync(accountApi.withdrawFeeExcluded(request));
    }
  }

  public void withdrawFiat(WithdrawFiatRequest request, boolean feeIncluded){
    if(feeIncluded){
      client.executeSync(accountApi.withdrawFiat(request));
    } else {
      client.executeSync(accountApi.withdrawFiatFeeExcluded(request));
    }
  }

  public List<TransactionFees> getFeesStructure(){
    return client.executeSync(accountApi.getFees()).getData();
  }

  /**
   *  This endpoint creates a new address even when the last created address is not used
   *  Not available by default, please contact support@whitebit.com
   */
  public DepositAddressResponse createDepositAddress(DepositAddressRequest req) {
    return client.executeSync(accountApi.getDepositAddress(req)).getData();
  }

  public String getWebSocketToken() {
    return client.executeSync(accountApi.getWebSocketToken()).getData().getToken();
  }
}
