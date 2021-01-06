package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.AgentLocation;

import java.util.UUID;
public interface AgentLocationRepository extends JpaRepository<AgentLocation, UUID> {
    AgentLocation findTopByAgentIdOrderByCreatedAtDesc(UUID agent_id);
}
