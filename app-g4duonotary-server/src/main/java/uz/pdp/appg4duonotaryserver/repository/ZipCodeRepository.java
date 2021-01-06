package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JaversSpringDataAuditable
public interface ZipCodeRepository extends JpaRepository<ZipCode, UUID> {
    Optional<ZipCode> findByCode(String code);

    List<ZipCode> findAllByCountyStateId(UUID state_id);

    List<ZipCode> findAllByCountyIdIn(Collection<UUID> county_id);

    List<ZipCode> findAllByCountyStateIdIn(Collection<UUID> county_state_id);

    @Query(value = "select CAST (st.id as varchar) stID from zip_code join county c on zip_code.county_id = c.id join state st on c.state_id = st.id group by st.id",nativeQuery = true)
    Page<String> getStateIds(Pageable pageable);

    @Query(value = "select CAST(zip_code.id as varchar) zipCodeId, zip_code.code from zip_code join county c on zip_code.county_id = c.id where c.state_id =:stateId",nativeQuery = true)
    List<Object[]> getZCodeAndId(@Param(value = "stateId")UUID stateId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "select * from zip_code where county_id=:countyId and active=true ")
    List<ZipCode> findAllByServiceIdAndCountyId(@Param("countyId") UUID countyId);
}
