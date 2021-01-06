package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.User;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SharingDiscountGivenDto {

    private UUID id;

    private UUID clientId;

    private Double percent;

    private Double amount;
}
