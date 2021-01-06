package uz.pdp.appg4duonotaryserver.payload;

import lombok.Data;
import java.util.UUID;

@Data
public class AgentOnlineOfflineDto {

    private UUID agentId;

    private boolean online;
}
