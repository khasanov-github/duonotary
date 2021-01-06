package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appg4duonotaryserver.entity.AgentHourOff;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@JaversSpringDataAuditable
public interface AgentHourOffRepository extends JpaRepository<AgentHourOff, UUID> {

    @Query(value = "select * from agent_hour_off where till_time >= date_trunc('day', cast(:selectedDate as date)) and till_time < date_trunc('day', cast(:selectedDate as date) + interval '1day')", nativeQuery = true)
    List<AgentHourOff> getAgentHourOffBySelectedDate(@Param("selectedDate") Date selectedDate);

    List<AgentHourOff>  findAllByAgentId(UUID id);
}
