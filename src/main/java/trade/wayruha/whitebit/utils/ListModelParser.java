package trade.wayruha.whitebit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import trade.wayruha.whitebit.exception.ModelParserException;

import java.util.ArrayList;
import java.util.List;

public class ListModelParser<T> extends ModelParser<List<T>> {
  public ListModelParser(ObjectMapper mapper, Class<T> type) {
    super(mapper);
    this.type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
  }

  @Override
  public List<T> parseUpdate(ArrayNode updateParams) throws ModelParserException {
    try{
      return mapper.convertValue(updateParams, type);
    }catch (Exception ex){
      throw new ModelParserException(updateParams, ex);
    }
  }
}
