package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZipCodeDto {

    private UUID id;

    private UUID countyId;

    private String code;

    private String name;

    private CountyDto countyDto;

    private boolean active;

//    private List<UUID> stateIds;
//
//    private List<UUID> countyIds;
//
//    private List<UUID> zipCodeIds;



    public ZipCodeDto(UUID id, String code, String name, CountyDto countyDto, boolean active) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.countyDto = countyDto;
        this.active = active;
    }

    public ZipCodeDto(UUID id, String code) {
        this.id = id;
        this.code = code;
    }
}
