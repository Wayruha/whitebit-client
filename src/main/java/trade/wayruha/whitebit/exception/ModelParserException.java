package trade.wayruha.whitebit.exception;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class ModelParserException extends Exception {
  private final ArrayNode node;

  public ModelParserException(String message, ArrayNode node) {
    super(message);
    this.node = node;
  }

  public ModelParserException(ArrayNode node, Throwable cause) {
    super(cause);
    this.node = node;
  }
}
