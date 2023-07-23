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
public class MarginAssetBalance extends AssetBalance{
  @JsonAlias({"borrow", "b"})
  private BigDecimal borrowed;
  @JsonAlias({"available_without_borrow", "av"})
  private BigDecimal transferable;
  @JsonAlias({"available_with_borrow", "ab"})
  private BigDecimal availableWithBorrow;

  public BigDecimal getLocked(){
    throw new NotImplementedException("Get Locked balance is not implemented for MarginAsset");
  }
}
