package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.AgentSchedule;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HourDto {

    private UUID id;

    private LocalTime fromTime;

    private LocalTime tillTime;

}
