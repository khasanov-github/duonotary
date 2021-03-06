package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.UserZipCode;

import java.util.List;
import java.util.UUID;

@JaversSpringDataAuditable
public interface UserZipCodeRepository extends JpaRepository<UserZipCode, UUID> {

    List<UserZipCode> findAllByUser_id(UUID user_id);

}
