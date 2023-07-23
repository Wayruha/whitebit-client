package trade.wayruha.whitebit.dto.ws;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import trade.wayruha.whitebit.domain.MarginAssetBalance;
import trade.wayruha.whitebit.utils.ModelParser;
import trade.wayruha.whitebit.exception.ModelParserException;
import trade.wayruha.whitebit.utils.ListModelParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class MarginBalanceUpdate {
  @JsonUnwrapped
  private final Map<String, MarginAssetBalance> balancesMap;

  public MarginBalanceUpdate() {
    this.balancesMap = new HashMap<>();
  }

  public void add(MarginAssetBalance asset){
    if(isNull(asset)) return;
    this.balancesMap.put(asset.getAsset(), asset);
  }

  public List<MarginAssetBalance> balances(){
    return new ArrayList<>(balancesMap.values());
  }

  public Map<String, MarginAssetBalance> asMap(){
    return new HashMap<>(balancesMap);
  }

  public static class Parser extends ModelParser<MarginBalanceUpdate> {
    private final ListModelParser<MarginAssetBalance> listParser;

    public Parser(ObjectMapper mapper) {
      super(mapper);
      this.listParser = new ListModelParser<>(mapper, MarginAssetBalance.class);
    }

    @Override
    public MarginBalanceUpdate parseUpdate(ArrayNode updateParams) throws ModelParserException {
      try {
        final List<MarginAssetBalance> list = listParser.parseUpdate(updateParams);
        final MarginBalanceUpdate result = new MarginBalanceUpdate();
        list.forEach(result::add);
        return result;
      } catch (Exception ex){
        throw new ModelParserException(updateParams, ex);
      }
    }
  }
}
