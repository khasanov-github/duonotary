package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.CustomDiscountGiven;

import java.util.UUID;
@JaversSpringDataAuditable
public interface CustomDiscountGivenRepository extends JpaRepository<CustomDiscountGiven, UUID> {
}
