package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.AdditionalService;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalServicePriceDto {

    private UUID id;

    private UUID additionalServiceId;

    private AdditionalServiceDto additionalServiceDto;

    private String additionalServiceName; // masalan : guvoh degan service nomi

    private Double price;

    private boolean active;

    private Double minPrice;

    private Double maxPrice;

    private boolean allZipCodes;

    private List<UUID> stateIds;

    private List<UUID> countyIds;

    private List<UUID> zipCodeIds;

    public AdditionalServicePriceDto(UUID additionalServiceId, AdditionalServiceDto additionalServiceDto, boolean active, Double minPrice, Double maxPrice) {
        this.additionalServiceId = additionalServiceId;
        this.additionalServiceDto = additionalServiceDto;
        this.active = active;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public AdditionalServicePriceDto(UUID id, UUID additionalServiceId, Double price, boolean active, boolean allZipCodes) {
        this.id = id;
        this.additionalServiceId = additionalServiceId;
        this.price = price;
        this.active = active;
        this.allZipCodes = allZipCodes;
    }

    public AdditionalServicePriceDto(UUID additionalServiceId, Double price, boolean active, boolean allZipCodes) {
        this.additionalServiceId = additionalServiceId;
        this.price = price;
        this.active = active;
        this.allZipCodes = allZipCodes;
    }

    public AdditionalServicePriceDto(UUID id, String additionalServiceName, Double price) {
        this.id = id;
        this.additionalServiceName = additionalServiceName;
        this.price = price;
    }
}
