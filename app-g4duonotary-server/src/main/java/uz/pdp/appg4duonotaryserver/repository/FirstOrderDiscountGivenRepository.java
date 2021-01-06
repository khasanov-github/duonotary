package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.FirstOrderDiscountGiven;

import java.util.UUID;
@JaversSpringDataAuditable
public interface FirstOrderDiscountGivenRepository extends JpaRepository<FirstOrderDiscountGiven, UUID> {

}
