package trade.wayruha.whitebit.dto.ws;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class WSTokenWrapper {
  @JsonAlias("websocket_token")
  private String token;
}
