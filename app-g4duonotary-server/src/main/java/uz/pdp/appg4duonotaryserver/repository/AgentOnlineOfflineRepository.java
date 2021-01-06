package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appg4duonotaryserver.entity.AdditionalServicePrice;
import uz.pdp.appg4duonotaryserver.entity.AgentOnlineOffline;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AgentOnlineOfflineRepository extends JpaRepository<AgentOnlineOffline, UUID> {
    List<AgentOnlineOffline> findAllByAgentId(UUID agent_id);


    @Query(value = "select created_at from agent_online_offline where agent_id=:id and created_at>=(select current_date)order by created_at desc limit 2",nativeQuery = true)
    List<Object[]> lastTwo(@Param("id")UUID id);
}
