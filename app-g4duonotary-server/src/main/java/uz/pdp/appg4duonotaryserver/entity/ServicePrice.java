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
public class ServicePrice extends AbsEntity {

    @ManyToOne(optional = false)
    private Service service;

    @ManyToOne(optional = false)
    private ZipCode zipCode;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer chargeMinute;

    @Column(nullable = false)
    private Integer chargePercent;

    boolean active;
}
