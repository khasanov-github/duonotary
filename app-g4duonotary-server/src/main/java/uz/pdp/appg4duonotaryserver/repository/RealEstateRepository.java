package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.RealEstate;

import java.util.UUID;

public interface RealEstateRepository extends JpaRepository<RealEstate, UUID> {
}
