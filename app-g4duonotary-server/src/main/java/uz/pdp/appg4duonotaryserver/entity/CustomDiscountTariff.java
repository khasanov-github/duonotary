package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CustomDiscountTariff extends AbsEntity {

    @OneToOne
    private User client;

    private Double percent;

    private boolean active;

    private boolean unlimited;

    private Integer givenCount;

    private Integer usedCount;
}
