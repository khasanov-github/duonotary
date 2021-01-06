package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.AgentSchedule;
import uz.pdp.appg4duonotaryserver.entity.Hour;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface HourRepository extends JpaRepository<Hour, UUID>{
    Hour findByAgentSchedule(AgentSchedule agentSchedule);
    List<Hour> findAllByAgentSchedule_Id(UUID agentSchedule_id);

}
