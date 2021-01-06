package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.Holiday;

import java.util.UUID;
@JaversSpringDataAuditable
public interface HolidayRepository extends JpaRepository<Holiday, UUID> {
    boolean existsByName(String name);
}
