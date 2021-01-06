package uz.pdp.appg4duonotaryserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appg4duonotaryserver.entity.Role;
import uz.pdp.appg4duonotaryserver.entity.enums.RoleName;

import java.awt.print.Pageable;
import java.util.Set;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Set<Role> findAllByName(RoleName name);


}
