package trade.wayruha.whitebit.service;

import trade.wayruha.whitebit.client.ApiClient;
import trade.wayruha.whitebit.dto.PageableResponse;
import trade.wayruha.whitebit.dto.request.Pageable;
import trade.wayruha.whitebit.dto.wbcode.WBCode;
import trade.wayruha.whitebit.dto.wbcode.WBCodeRecord;
import trade.wayruha.whitebit.dto.wbcode.WBCodeRequest;
import trade.wayruha.whitebit.dto.wbcode.WBCodeResponse;
import trade.wayruha.whitebit.service.endpoint.WBCodesEndpoint;

public class WhitebitCodeService extends ServiceBase {
  private final WBCodesEndpoint api;

  public WhitebitCodeService(ApiClient client) {
    super(client);
    this.api = createService(WBCodesEndpoint.class);
  }

  public WBCodeResponse createCode(WBCodeRequest request){
    return client.executeSync(api.createCode(request)).getData();
  }

  public WBCodeResponse applyCode(WBCode request){
    return client.executeSync(api.applyCode(request)).getData();
  }

  public PageableResponse<WBCodeRecord> getMyCodes(Pageable pageable){
    return client.executeSync(api.getMyCodes(pageable)).getData();
  }

  public PageableResponse<WBCodeRecord> getCodesHistory(Pageable pageable){
    return client.executeSync(api.getCodesHistory(pageable)).getData();
  }
}
