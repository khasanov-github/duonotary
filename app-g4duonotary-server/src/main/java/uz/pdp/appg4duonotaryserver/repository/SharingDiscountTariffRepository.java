package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appg4duonotaryserver.entity.SharingDiscountTariff;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JaversSpringDataAuditable
public interface SharingDiscountTariffRepository extends JpaRepository<SharingDiscountTariff, UUID> {
    Optional<SharingDiscountTariff> findByOnline(boolean online);
    boolean existsByZipCode(ZipCode zipCode);
    SharingDiscountTariff findByZipCode(ZipCode zipCode);
    Optional<SharingDiscountTariff> findByZipCodeCode(String zipCode_code);


//    @Query(value = "select online,min(percent),max(percent),count(active)from sharing_discount_tariff where active group by online", nativeQuery = true)
//    List<Object[]> getMinMaxPercentWhereActiveGroupByOnline();

    @Query(value = "select coalesce(min(percent),0)as minPercent,coalesce(max(percent),0)as maxPercent,count(active)as activeCount from sharing_discount_tariff where active=true and online=false", nativeQuery = true)
    List<Object[]> getMinMaxPercentWhereActiveTrueAndOnlineFalse();
}
