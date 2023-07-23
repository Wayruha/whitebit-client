package trade.wayruha.whitebit.dto.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.exception.ModelParserException;
import trade.wayruha.whitebit.utils.ModelParser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KlineUpdate {
  private long timestamp;
  private Market market;
  private BigDecimal open;
  private BigDecimal close;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal stockVolume;
  private BigDecimal volumeTotal;

  public static class Parser extends ModelParser<List<KlineUpdate>> {

    public Parser(ObjectMapper mapper) {
      super(mapper);
    }

    @Override
    public List<KlineUpdate> parseUpdate(ArrayNode updateParams) throws ModelParserException {
      try {
        List<KlineUpdate> list = new ArrayList<>();
        for(JsonNode node : updateParams){
          list.add(KlineUpdate.builder()
              .timestamp(node.get(0).asLong())
              .open(createBigDecimal(node.get(1).asText()))
              .close(createBigDecimal(node.get(2).asText()))
              .high(createBigDecimal(node.get(3).asText()))
              .low(createBigDecimal(node.get(4).asText()))
              .stockVolume(createBigDecimal(node.get(5).asText()))
              .volumeTotal(createBigDecimal(node.get(6).asText()))
              .market(Market.parse(node.get(7).asText()))
              .build());
        }
        return list;
      } catch (Exception ex) {
        throw new ModelParserException(updateParams, ex);
      }
    }
  }
}
