package trade.wayruha.whitebit.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import trade.wayruha.whitebit.exception.ModelParserException;

public abstract class ModelParser<T> {
  protected final ObjectMapper mapper;

  public ModelParser(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public abstract T parseUpdate(ArrayNode updateParams) throws ModelParserException;
}
