package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import uz.pdp.appg4duonotaryserver.entity.LoyaltyDiscountTariff;
import uz.pdp.appg4duonotaryserver.projection.CustomLoyaltyDiscountTariff;

import java.util.Optional;
import java.util.UUID;
@JaversSpringDataAuditable
@CrossOrigin
@RepositoryRestResource(path = "loyaltyDiscountTariff", collectionResourceRel = "list", excerptProjection = CustomLoyaltyDiscountTariff.class)
public interface LoyaltyDiscountTariffRepository extends JpaRepository<LoyaltyDiscountTariff, UUID> {

    @Query(value = "select * from loyalty_discount_tariff order by created_at desc limit 1", nativeQuery = true)
    LoyaltyDiscountTariff selectFirst();

//    @Query(value = "select * from orders where client_id=:client_id and created_at >=:month_from and created_at<= :month_to and order_status=:order_status ", nativeQuery = true)



    Optional<LoyaltyDiscountTariff> findAllByActiveTrue();
}
