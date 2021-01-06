package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomDiscountGivenDtoAsomiddin {
    private Double percent;

    private UserDto clientDto;

    private Double amount;

    private boolean fromTariff;
}
