package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.appg4duonotaryserver.entity.AdditionalService;
import uz.pdp.appg4duonotaryserver.entity.AdditionalServicePrice;
import uz.pdp.appg4duonotaryserver.entity.Service;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JaversSpringDataAuditable
public interface AdditionalServicePriceRepository extends JpaRepository<AdditionalServicePrice, UUID> {
    boolean existsByZipCodeAndAdditionalService(ZipCode zipCode, AdditionalService additionalService);

//    AdditionalServicePrice findByZipCodeAndAdditionalService(ZipCode zipCode, AdditionalService additionalService);

    //    @Modifying
//    @Transactional
    @Query(value = "select cast (ads.id as varchar) as adsId,ads.name, min(price),max(price),(select count(*) from additional_service_price where additional_service_id=ads.id and active=true) from additional_service_price join additional_service ads on additional_service_price.additional_service_id = ads.id group by ads.id", nativeQuery = true)
    List<Object[]> getAdditionalServicePriceGroupByMinMaxPrice();

    Optional<AdditionalServicePrice> findByZipCodeAndAdditionalService(ZipCode zipCode, AdditionalService additionalService);
    Optional<AdditionalServicePrice> findByZipCodeAndAdditionalServiceAndActiveTrue(ZipCode zipCode, AdditionalService additionalService);
}
