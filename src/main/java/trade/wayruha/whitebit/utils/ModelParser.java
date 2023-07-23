package trade.wayruha.whitebit.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import trade.wayruha.whitebit.exception.ModelParserException;

public abstract class ModelParser<T> {
  protected final ObjectMapper mapper;
  protected JavaType type;

  public ModelParser(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public ModelParser(ObjectMapper mapper, Class<T> clazz) {
    this(mapper);
    this.type = mapper.constructType(clazz);
  }

  public abstract T parseUpdate(ArrayNode updateParams) throws ModelParserException;
}
