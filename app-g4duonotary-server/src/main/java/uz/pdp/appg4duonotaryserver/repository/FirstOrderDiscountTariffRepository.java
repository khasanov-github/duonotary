package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appg4duonotaryserver.entity.FirstOrderDiscountTariff;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@JaversSpringDataAuditable
public interface FirstOrderDiscountTariffRepository extends JpaRepository<FirstOrderDiscountTariff, UUID> {
    boolean existsByZipCode(ZipCode zipCode);
    FirstOrderDiscountTariff findByZipCode(ZipCode zipCode);
    Optional<FirstOrderDiscountTariff> findByZipCodeCode(String zipCode_code);
    Optional<FirstOrderDiscountTariff> findByOnline(boolean online);


 @Query(value = "select min(percent), max(percent), (select count(*) from first_order_discount_tariff where active = true and online=false) from first_order_discount_tariff where online=false", nativeQuery = true)
 List<Object[]>  getMinMaxPercentDiscountFirstOrderDiscountTariffAndActive();




}
