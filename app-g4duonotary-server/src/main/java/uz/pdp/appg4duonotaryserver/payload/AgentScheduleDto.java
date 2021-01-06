package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.enums.WeekDay;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentScheduleDto {
    private UUID id;

    private WeekDay day;

    private List<HourDto> hourList;

    private boolean dayoff;

}
