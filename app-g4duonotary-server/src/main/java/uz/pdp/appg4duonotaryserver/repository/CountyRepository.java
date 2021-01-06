package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appg4duonotaryserver.entity.County;

import java.util.List;
import java.util.UUID;
@JaversSpringDataAuditable
public interface CountyRepository  extends JpaRepository<County, UUID> {
    @Query(nativeQuery = true,
            value = "select * from county where active=true and state_id=:stateId " )
//                    "and id in" +
//                    "(select county_id from zip_code where active=true and id in(select zip_code_id " +
//                    "from service_price sp where sp.active=true and sp.service_id=:serviceId))")
    List<County> findAllByStateId(@Param("stateId") UUID stateId) ;
}
