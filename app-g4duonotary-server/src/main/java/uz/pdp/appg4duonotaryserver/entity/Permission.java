package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.appg4duonotaryserver.entity.enums.PermissionName;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private PermissionName permissionName;

    public Permission(PermissionName permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String getAuthority() {
        return this.permissionName.name();
    }
}
