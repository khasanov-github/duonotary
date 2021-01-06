package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidayDto {
    private UUID id;
    private String name;
    private boolean active;
    private String description;
    private UUID mainServiceId;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date date;
}
