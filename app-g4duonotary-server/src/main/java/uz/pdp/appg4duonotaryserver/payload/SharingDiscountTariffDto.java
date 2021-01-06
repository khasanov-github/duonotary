package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SharingDiscountTariffDto {

    private UUID id;

    private boolean active;

    private boolean online;

    private boolean allZipCode;

    private Double percent;

    private Double minPercent;

    private Double maxPercent;

    private List<UUID> stateIds;

    private List<UUID> countyIds;

    private List<UUID> zipCodeIds;

    private ZipCodeDto zipCodeDto;



    public SharingDiscountTariffDto(UUID id, boolean active, boolean online, Double percent, boolean isAllZipCode, List<UUID> stateIds, List<UUID> countyIds, List<UUID> zipCodeIds) {
        this.id = id;
        this.active = active;
        this.online = online;
        this.percent = percent;
        this.allZipCode = isAllZipCode;
        this.stateIds = stateIds;
        this.countyIds = countyIds;
        this.zipCodeIds = zipCodeIds;
    }

    public SharingDiscountTariffDto(UUID id, boolean active, boolean online, Double percent, UUID zip) {
        this.id = id;
        this.active = active;
        this.online = online;
        this.percent = percent;
//        this.zip = zip;
    }
}
