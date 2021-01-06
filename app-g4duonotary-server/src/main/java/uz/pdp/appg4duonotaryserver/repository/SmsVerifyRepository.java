package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.SmsVerify;

import java.util.Optional;

public interface SmsVerifyRepository extends JpaRepository<SmsVerify,Integer> {
    Optional<SmsVerify> findByPhoneNumber(String phoneNumber);
}
