package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appg4duonotaryserver.entity.AdditionalService;
import uz.pdp.appg4duonotaryserver.entity.Service;
import uz.pdp.appg4duonotaryserver.projection.CustomAdditionalService;

import java.util.List;
import java.util.UUID;
@JaversSpringDataAuditable
@RepositoryRestResource(path = "additionalService",collectionResourceRel = "list",excerptProjection = CustomAdditionalService.class)
public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, UUID> {
    List<AdditionalService> findAllByServices(List<Service> services);
}
