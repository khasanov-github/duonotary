package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsNameEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderAdditionalService extends AbsNameEntity {
    //Qo'shimcha zakarlar uchun

    @ManyToOne(optional = false)
    private Order order;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private Double currentPrice;

    @ManyToOne(optional = false)
    private AdditionalServicePrice additionalServicePrice;
}
