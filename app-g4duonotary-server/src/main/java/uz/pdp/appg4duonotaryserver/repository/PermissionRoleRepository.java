package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.AdditionalServicePrice;
import uz.pdp.appg4duonotaryserver.entity.PermissionRole;

import java.util.UUID;

public interface PermissionRoleRepository extends JpaRepository<PermissionRole, UUID> {
}
