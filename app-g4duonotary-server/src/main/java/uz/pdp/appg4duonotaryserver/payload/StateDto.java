package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateDto {
    private UUID id;
    private String name;
    private String description;

    public StateDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
