package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FirstOrderDiscountGivenDto {

    private UUID id;

    private UUID clientId;
    private String clientFirstName;
    private String clientLastName;

    private Double percent;

    private Double amount;

    private boolean allZipCodes;

    private List<UUID> states;

    private List<UUID> counties;

    private List<UUID> zipCodes;

    public FirstOrderDiscountGivenDto(UUID id, UUID clientId, String clientFirstName, String clientLastName, Double percent, Double amount) {
        this.id = id;
        this.clientId = clientId;
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.percent = percent;
        this.amount = amount;
    }
}
