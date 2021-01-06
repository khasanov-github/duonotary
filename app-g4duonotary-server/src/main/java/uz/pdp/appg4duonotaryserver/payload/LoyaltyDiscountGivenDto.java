package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoyaltyDiscountGivenDto {

    private UserDto client;//

    private Double percent;//

    private Double amount;//

    private Double totalSum;//
}
