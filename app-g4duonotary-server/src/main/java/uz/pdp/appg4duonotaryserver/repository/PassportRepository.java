package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.Passport;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JaversSpringDataAuditable
public interface PassportRepository extends JpaRepository<Passport, UUID> {
    Optional<Passport> findByUser_Id(UUID user_id);
}
