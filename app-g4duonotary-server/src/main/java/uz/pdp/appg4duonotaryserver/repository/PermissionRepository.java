package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
