package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountyDtoAsomiddin {
    private UUID id;

    private String name;

    private UUID stateId;

//    private StateDto stateDto;
}
