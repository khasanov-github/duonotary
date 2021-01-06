package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.International;

import java.util.UUID;

public interface InternationalRepository extends JpaRepository<International, UUID> {
}
