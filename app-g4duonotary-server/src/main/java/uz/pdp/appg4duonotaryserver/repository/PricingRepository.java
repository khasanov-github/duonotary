package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appg4duonotaryserver.entity.Pricing;

import java.util.List;
import java.util.UUID;

public interface PricingRepository extends JpaRepository<Pricing, UUID> {
    List<Pricing> findAllByServicePriceIdAndActiveTrue(UUID servicePrice_id);

    @Query(value = "select price from pricing where till_count IS NULL and active=true and service_price_id=:id", nativeQuery = true)
    Double getPriceByTillCountIsNull(UUID id);


}
