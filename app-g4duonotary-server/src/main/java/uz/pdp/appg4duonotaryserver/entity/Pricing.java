package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pricing extends AbsEntity {
    @ManyToOne(optional = false)
    private ServicePrice servicePrice;

    @Column(nullable = false)
    private Double price;

    private boolean active;

    private Integer fromCount;

    private Integer tillCount;


}
