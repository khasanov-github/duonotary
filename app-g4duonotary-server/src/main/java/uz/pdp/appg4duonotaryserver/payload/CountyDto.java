package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountyDto {
    private UUID id;

    private String name;

    private StateDto stateDto;

    public CountyDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
