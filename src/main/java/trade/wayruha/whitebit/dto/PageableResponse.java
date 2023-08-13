package trade.wayruha.whitebit.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import trade.wayruha.whitebit.dto.request.Pageable;

import java.util.List;

@Data
@ToString(callSuper = true)
public class PageableResponse<T> extends Pageable {
  @JsonProperty("total")
  private int totalAvailable;
  @JsonAlias({"data", "records"})
  private List<T> data;
}
