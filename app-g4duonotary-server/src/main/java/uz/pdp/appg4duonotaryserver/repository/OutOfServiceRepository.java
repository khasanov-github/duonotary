package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.OutOfService;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;

import java.util.Optional;
import java.util.UUID;

@JaversSpringDataAuditable
public interface OutOfServiceRepository extends JpaRepository<OutOfService, UUID> {

    Optional<OutOfService> findByZipCodeIdAndEmail(UUID zipCode_id, String email);

}
