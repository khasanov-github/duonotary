package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appg4duonotaryserver.entity.LoyaltyDiscountGiven;
import uz.pdp.appg4duonotaryserver.entity.User;

import java.util.Optional;
import java.util.UUID;

@JaversSpringDataAuditable
public interface LoyaltyDiscountGivenRepository extends JpaRepository<LoyaltyDiscountGiven, UUID> {

    Optional<LoyaltyDiscountGiven> findAllByClient(User client);

    Optional<LoyaltyDiscountGiven> findByClient(User client);

    @Query(value = "select * from loyalty_discount_given order by created_at desc", nativeQuery = true)
       Page<LoyaltyDiscountGiven> findAllOrderByCreatedAtDesc(Pageable pageable);



}
