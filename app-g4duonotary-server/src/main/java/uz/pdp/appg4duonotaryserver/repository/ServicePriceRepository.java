package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appg4duonotaryserver.entity.Service;
import uz.pdp.appg4duonotaryserver.entity.ServicePrice;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JaversSpringDataAuditable
public interface ServicePriceRepository extends JpaRepository<ServicePrice, UUID> {
    boolean existsByZipCodeAndService(ZipCode zipCode, Service service);

    boolean existsByService(Service service);

    ServicePrice findByZipCodeAndService(ZipCode zipCode, Service service);
    ZipCode findByZipCodeId(UUID zipCodeId);

//
//    @Query(value = "select min(price), max(price),(select count(*) from service_price where active = true)" +
//            " from service_price where service_id =: serviceId",nativeQuery = true)
//    List<Object[]> getMinMaxPriceAndActiveByServiceId(@Param(value = "serviceId") UUID serviceId);

    @Query(value = "select CAST(s.id as varchar) service_id, min(price), max(price), (select count(*) from service_price where service_id = s.id and active = true) " +
            "from service_price join services s on service_price.service_id = s.id group by s.id", nativeQuery = true)
    List<Object[]> getMinMaxPriceAndActiveGroupByServiceId();

    Optional<ServicePrice> findByIdAndActiveTrue(UUID servicePriceId);



}
