package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Data
@NoArgsConstructor
public class AssetBalance {
  protected String asset;
  @JsonAlias({"available", "balance", "B"})
  protected BigDecimal available = ZERO;
  protected BigDecimal freeze = ZERO;

  public AssetBalance(String asset, BigDecimal available, BigDecimal freeze) {
    this.asset = asset;
    this.available = available != null ? available : ZERO;
    this.freeze = freeze != null ? freeze : ZERO;
  }

  public BigDecimal total(){
    return available.add(freeze);
  }

  public boolean isEmpty(){
    return total().signum() == 0;
  }

  public void setAvailable(String available) {
    this.available = new BigDecimal(available);
  }
}
