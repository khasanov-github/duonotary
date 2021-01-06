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
public class DiscountExpense extends AbsEntity {
    //Bu erda clientni yig'lib qolgan
    // discountlarini sarflashi yoziladi.

    @ManyToOne
    private User client;

    @OneToOne
    private Order order;

    private Double percent;

    private Double amount;

}
