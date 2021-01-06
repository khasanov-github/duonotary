package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.enums.PayStatus;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Payment extends AbsEntity {

    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @ManyToOne(optional = false)
    private Order order;

    @Column(nullable = false)
    private Double sum;
}
