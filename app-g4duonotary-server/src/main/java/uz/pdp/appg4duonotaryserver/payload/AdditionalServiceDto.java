package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalServiceDto {
    private String name;

    private boolean active;

    private String description;

    private List<ServiceDto> serviceDtos;

    public AdditionalServiceDto(String name, boolean active, String description) {
        this.name = name;
        this.active = active;
        this.description = description;
    }
}
