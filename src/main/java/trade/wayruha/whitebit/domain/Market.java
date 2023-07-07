package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.Value;

import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Value
@JsonSerialize(using = Market.Serializer.class)
@JsonDeserialize(using = Market.Deserializer.class)
public class Market {
    public static final String MARKET_SPLITTER = "_";
    String baseAsset;
    String quoteAsset;

    public Market(String baseAsset, String quoteAsset) {
        Objects.requireNonNull(baseAsset);
        Objects.requireNonNull(quoteAsset);
        this.baseAsset = baseAsset.toUpperCase();
        this.quoteAsset = quoteAsset.toUpperCase();
    }

    public static Market parse(String str) {
        final String[] parts = str.split(MARKET_SPLITTER);
        if (parts.length != 2) throw new IllegalArgumentException("Parse exception: invalid market " + str);
        return new Market(parts[0], parts[1]);
    }

    @Override
    public String toString() {
        return baseAsset + MARKET_SPLITTER + quoteAsset;
    }

    public static class Serializer extends StdSerializer<Market> {
        public Serializer() {
            super(Market.class);
        }

        @Override
        public void serialize(Market value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (nonNull(value)) {
                gen.writeString(value.toString());
            } else {
                gen.writeNull();
            }
        }
    }

    public static class Deserializer extends StdDeserializer<Market> {
        public Deserializer() {
            super(Market.class);
        }

        @Override
        public Market deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            final String marketStr = p.readValueAs(String.class);
            return Market.parse(marketStr);
        }
    }
}
