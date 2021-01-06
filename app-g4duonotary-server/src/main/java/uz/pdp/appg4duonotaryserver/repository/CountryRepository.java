package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.Country;

import java.util.UUID;
@JaversSpringDataAuditable
public interface CountryRepository extends JpaRepository<Country, UUID> {
    boolean existsByNameEqualsIgnoreCase(String name);
    boolean existsByNameEqualsIgnoreCaseAndIdNot(String name, UUID id);

}
