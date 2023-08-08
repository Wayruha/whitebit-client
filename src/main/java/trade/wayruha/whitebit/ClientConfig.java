package trade.wayruha.whitebit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientConfig {
  /**
   * common ObjectMapper. Can be replaced by clients for a custom one
   */
  @Getter
  @Setter
  private static ObjectMapper objectMapper = createObjectMapper();

  @Getter
  @Setter
  private static boolean enableNonceWindow = true;

  /**
   * return current Unix epoch time in millis.
   * It's preferred to call this method instead of obtaining it by yourself.
   * Some `aligning` logic may be added in future (in case if there's a big discrepancy between local and server's time)
   */
  public static long getCurrentTime() {
    return System.currentTimeMillis();
  }

  private static ObjectMapper createObjectMapper() {
    final ObjectMapper mapper = new ObjectMapper();
    mapper
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    return mapper;
  }
}
