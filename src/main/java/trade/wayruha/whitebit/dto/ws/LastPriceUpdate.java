package trade.wayruha.whitebit.dto.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;
import trade.wayruha.whitebit.domain.Market;
import trade.wayruha.whitebit.exception.ModelParserException;
import trade.wayruha.whitebit.utils.ModelParser;

import java.math.BigDecimal;

@Data
public class LastPriceUpdate {
  private Market market;
  private BigDecimal price;

  public static class Parser extends ModelParser<LastPriceUpdate> {

    public Parser(ObjectMapper mapper) {
      super(mapper, LastPriceUpdate.class);
    }

    @Override
    public LastPriceUpdate parseUpdate(ArrayNode updateParams) throws ModelParserException {
      try {
        if (updateParams.size() != 2) throw new IllegalArgumentException("Exactly 2 elements expected");
        final LastPriceUpdate dto = new LastPriceUpdate();
        final String marketStr = updateParams.get(0).asText();
        final String priceStr = updateParams.get(1).asText();
        dto.setMarket(Market.parse(marketStr));
        dto.setPrice(new BigDecimal(priceStr));
        return dto;
      } catch (Exception ex) {
        throw new ModelParserException(updateParams, ex);
      }
    }
  }
}
