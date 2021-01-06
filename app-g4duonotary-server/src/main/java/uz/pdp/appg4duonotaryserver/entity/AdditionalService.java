package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsNameEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdditionalService extends AbsNameEntity {

    @ManyToMany
    @JoinTable(name = "additional_service_service",
            joinColumns = {@JoinColumn(name = "additional_service_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")})
    private List<Service> services;//Qoshimcha xizmat qaysi servislada ko'rsatilishi

}