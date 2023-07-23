package trade.wayruha.whitebit.dto.ws;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.domain.MarketTrade;
import trade.wayruha.whitebit.exception.ModelParserException;
import trade.wayruha.whitebit.utils.ModelParser;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketTradesUpdate {
  private Market market;
  private List<MarketTrade> trades;

  public static class Parser extends ModelParser<MarketTradesUpdate> {
    private final JavaType tradesListType;
    public Parser(ObjectMapper mapper) {
      super(mapper);
      this.tradesListType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, MarketTrade.class);
    }

    @Override
    public MarketTradesUpdate parseUpdate(ArrayNode updateParams) throws ModelParserException {
      try {
        final Market market = Market.parse(updateParams.get(0).asText());
        final ArrayNode tradesNode = (ArrayNode) updateParams.get(1);
        final List<MarketTrade> trades = mapper.convertValue(tradesNode, tradesListType);
        return new MarketTradesUpdate(market, trades);
      } catch (Exception ex) {
        throw new ModelParserException(updateParams, ex);
      }
    }
  }
}
