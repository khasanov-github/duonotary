package uz.pdp.appg4duonotaryserver.payload;

import lombok.Data;
import uz.pdp.appg4duonotaryserver.entity.User;

import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
public class AgentLocationDto {
    private UUID agentId;

    private Float lan;

    private Float lat;
}
