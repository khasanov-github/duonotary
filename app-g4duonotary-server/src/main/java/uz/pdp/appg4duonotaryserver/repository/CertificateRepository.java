package uz.pdp.appg4duonotaryserver.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.Certificate;

import java.util.List;
import java.util.UUID;
@JaversSpringDataAuditable
public interface CertificateRepository  extends JpaRepository<Certificate, UUID> {

    List<Certificate> findAllByUser_Id(UUID user_id);
}
