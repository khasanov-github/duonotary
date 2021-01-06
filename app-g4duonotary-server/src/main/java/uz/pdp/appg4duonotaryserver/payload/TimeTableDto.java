package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeTableDto {
    private UUID id;

    private UserDto agentDto;

    private String fromTime;

    private String tillTime;

    private OrderDto orderDto;

    private boolean tempBooked;

    private boolean booked;
}
