package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import uz.pdp.appg4duonotaryserver.entity.enums.RoleName;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbsEntity implements GrantedAuthority {
    @Enumerated(EnumType.STRING)
    private RoleName name;

    @Override
    public String getAuthority() {
        return this.name.name();
    }
}
