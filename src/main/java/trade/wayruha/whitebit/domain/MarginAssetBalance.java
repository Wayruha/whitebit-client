package trade.wayruha.whitebit.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.NotImplementedException;

import java.math.BigDecimal;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MarginAssetBalance extends AssetBalance {
  @JsonAlias({"borrow", "b"})
  private BigDecimal borrowed = BigDecimal.ZERO;
  @JsonAlias({"available_without_borrow", "av", "availableWithoutBorrow"})
  private BigDecimal transferable = BigDecimal.ZERO;
  @JsonAlias({"available_with_borrow", "ab", "availableWithBorrow"})
  private BigDecimal availableWithBorrow = BigDecimal.ZERO;

  @Override
  public boolean isEmpty() {
    return super.isEmpty() && borrowed.signum() == 0;
  }

  public BigDecimal getLocked() {
    throw new NotImplementedException("Get Locked balance is not implemented for MarginAsset");
  }
}
