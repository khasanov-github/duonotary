package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
    private UUID id;

    private String name;

    private String abbr;

    private boolean active;

    private boolean embassy;

}
