package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Lightweight wrapper for a generic value.
 * WhiteBit may respond with an array consisting of a single value (the response either object or array)
 * This class is used to parse such values as objects
 */
@JsonDeserialize(using = ValueWrapper.ValueDeserializer.class)
public class ValueWrapper<T> {
  private final List<T> list;

  public ValueWrapper(List<T> wrappedValue) {
    if (wrappedValue.size() != 1) throw new IllegalArgumentException("There should be exactly one content item");
    this.list = wrappedValue;
  }

  public ValueWrapper(T val) {
    this.list = List.of(val);
  }

  public T getValue() {
    Objects.requireNonNull(list);
    if (list.size() != 1) throw new IllegalStateException("Value-Wrapper contains few values: " + list);
    return list.get(0);
  }


  public static class ValueDeserializer<T> extends JsonDeserializer<ValueWrapper<T>> {
    private final TypeReference<List<T>> listRef = new TypeReference<>() {
    };

    @Override
    public ValueWrapper<T> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
      final List<T> content = jsonParser.readValueAs(listRef);
      return new ValueWrapper<>(content);
    }
  }
}
