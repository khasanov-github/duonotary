package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutOfServiceDto {

    private UUID zipCodeId;

    private String email;

}
