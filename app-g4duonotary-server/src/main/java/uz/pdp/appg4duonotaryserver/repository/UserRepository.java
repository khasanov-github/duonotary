package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.appg4duonotaryserver.entity.Role;
import uz.pdp.appg4duonotaryserver.entity.User;

import java.util.*;

@JaversSpringDataAuditable
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID userId);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID userId);

    Optional<User> findByEmailCode(String emailCode);

    Page<User> findAllByRolesIn(Set<Role> roles,Pageable pageable);
    User findByIdAndRolesIn(UUID id, Set<Role> roles);

    @Query(value = "select * from users where lower(first_name) like (select concat('%',:str,'%')) or lower(last_name) like (select concat('%',:str,'%'))",nativeQuery = true)
    List<User> searchAgents(String str);


}
