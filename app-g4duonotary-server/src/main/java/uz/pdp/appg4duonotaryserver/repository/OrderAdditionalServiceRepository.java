package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.OrderAdditionalService;

import java.util.UUID;

public interface OrderAdditionalServiceRepository extends JpaRepository<OrderAdditionalService, UUID> {
}
