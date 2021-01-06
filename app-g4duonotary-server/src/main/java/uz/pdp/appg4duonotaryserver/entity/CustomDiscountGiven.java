package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CustomDiscountGiven extends AbsEntity {

    @ManyToOne
    private User client;

    private Double percent;

    private Double amount;

    private boolean fromTariff;//Kompaniya aybi bilan bo'lsa fromTariff false
    // va qaysi order uchun berganini aniq belgilaydi

    @OneToOne
    private Order order;





}
