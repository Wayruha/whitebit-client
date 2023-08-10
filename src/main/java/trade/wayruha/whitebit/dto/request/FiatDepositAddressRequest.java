package trade.wayruha.whitebit.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FiatDepositAddressRequest {
  private String ticker;
  private String provider;
  private BigDecimal amount;
  private String uniqueId;
  //required if provider is VISAMASTER
  private Customer customer;
  //Customer will be redirected to this URL by acquiring provider after success deposit. To activate this feature, please contact support
  private String successLink;
  //Customer will be redirected to this URL in case of fail or rejection on acquiring provider side. To activate this feature, please contact support
  private String failureLink;
  //Customer will be redirected to the URL defined if selects 'back' option after from the payment success or failure page.
  // To activate this feature, define desired link. If not populated, option 'back' won't be displayed
  private String returnLink;

  @Value
  public static class Customer {
    String firstName;
    String lastName;
    String email;
  }
}
