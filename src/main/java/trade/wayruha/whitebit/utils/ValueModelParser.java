package trade.wayruha.whitebit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import trade.wayruha.whitebit.exception.ModelParserException;

public class ValueModelParser<T> extends ModelParser<T> {
  private final ListModelParser<T> listParser;

  public ValueModelParser(ObjectMapper mapper, Class<T> type) {
    super(mapper);
    this.listParser = new ListModelParser<>(mapper, type);
  }

  @Override
  public T parseUpdate(ArrayNode updateParams) throws ModelParserException {
    try {
      return listParser.parseUpdate(updateParams).stream().findFirst().orElse(null);
    } catch (Exception ex) {
      throw new ModelParserException(updateParams, ex);
    }
  }
}
