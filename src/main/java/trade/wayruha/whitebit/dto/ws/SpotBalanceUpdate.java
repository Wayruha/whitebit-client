package trade.wayruha.whitebit.dto.ws;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import trade.wayruha.whitebit.domain.AssetBalance;
import trade.wayruha.whitebit.utils.ModelParser;
import trade.wayruha.whitebit.exception.ModelParserException;

import java.util.*;

import static java.util.Objects.isNull;

@ToString
@EqualsAndHashCode
@Getter
public class SpotBalanceUpdate {
  @JsonUnwrapped
  private final Map<String, AssetBalance> balancesMap;

  public SpotBalanceUpdate() {
    this.balancesMap = new HashMap<>();
  }

  public void add(AssetBalance asset){
    if(isNull(asset)) return;
    this.balancesMap.put(asset.getAsset(), asset);
  }

  public List<AssetBalance> balances(){
    return new ArrayList<>(balancesMap.values());
  }

  public Map<String, AssetBalance> asMap(){
    return new HashMap<>(balancesMap);
  }

  public static class Parser extends ModelParser<SpotBalanceUpdate> {
    private final JavaType balanceType;

    public Parser(ObjectMapper mapper) {
      super(mapper);
      this.balanceType = mapper.getTypeFactory().constructParametricType(Map.class, String.class, AssetBalance.class);
    }

    @Override
    public SpotBalanceUpdate parseUpdate(ArrayNode updateParams) throws ModelParserException {
      try {
        final SpotBalanceUpdate result = new SpotBalanceUpdate();
        for (JsonNode node : updateParams) {
          final Map<String, AssetBalance> map = mapper.convertValue(node, balanceType);
          map.forEach((asset, balance) -> {
            balance.setAsset(asset);
            result.add(balance);
          });
        }
        return result;
      } catch (Exception ex){
        throw new ModelParserException(updateParams, ex);
      }
    }
  }
}
