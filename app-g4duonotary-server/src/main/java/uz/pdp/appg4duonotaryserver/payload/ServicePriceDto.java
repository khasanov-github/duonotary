package uz.pdp.appg4duonotaryserver.payload;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ServicePriceDto {
    private UUID id;
    private UUID serviceId;

    private ServiceDto serviceDto;
    private Double price;
    private Double minPrice;
    private Double maxPrice;
    private Integer chargeMinute;
    private Integer chargePercent;
    private boolean allZipCodes;
    private boolean active;
    private List<UUID> stateIds;

    private List<UUID> countyIds;

    private List<UUID> zipCodeIds;

}
