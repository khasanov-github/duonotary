package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull
public class MainServiceDto {
    private UUID id;

    private String name;

    private LocalTime fromTime;

    private LocalTime tillTime;

    private boolean online;

    private Integer orderNumber;

    private boolean active;

    private String description;
}
