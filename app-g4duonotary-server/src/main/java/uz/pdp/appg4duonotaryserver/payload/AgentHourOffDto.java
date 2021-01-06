package uz.pdp.appg4duonotaryserver.payload;

import lombok.Data;
import uz.pdp.appg4duonotaryserver.entity.User;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class AgentHourOffDto {
    private UUID agentId;

    private Timestamp fromTime;

    private Timestamp tillTime;

}
