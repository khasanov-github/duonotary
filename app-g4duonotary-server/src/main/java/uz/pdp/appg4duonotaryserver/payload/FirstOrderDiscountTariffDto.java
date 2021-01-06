package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FirstOrderDiscountTariffDto {

    private UUID id;

    private Double percent;

    private ZipCodeDto zipCodeDto;

    private boolean active;

    private boolean online;

    private Double minPercent;

    private Double maxPercent;

    private boolean allZipCodes;

    private List<UUID> states;

    private List<UUID> counties;

    private List<UUID> zipCodes;

    public FirstOrderDiscountTariffDto(UUID id, boolean active, Double percent, ZipCodeDto zipCodeDto) {
        this.id = id;
        this.active = active;
        this.percent = percent;
        this.zipCodeDto = zipCodeDto;
    }

    public FirstOrderDiscountTariffDto(Double minPercent, Double maxPercent, boolean active) {
        this.minPercent = minPercent;
        this.maxPercent = maxPercent;
        this.active = active;
    }
    public FirstOrderDiscountTariffDto(UUID id, boolean active, boolean online, Double percent) {
        this.id = id;
        this.active = active;
        this.active = online;
        this.percent = percent;
    }
}
