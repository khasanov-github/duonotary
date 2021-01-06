package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;
import uz.pdp.appg4duonotaryserver.entity.template.AbsNameEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "services")
public class Service extends AbsNameEntity {

    @ManyToOne(optional = false)
    private MainService mainService; //servislar (online or in-person)

    @Column(nullable = false)
    private  Integer initialCount; //xizmatlar soni;

    @Column(nullable = false)
    private Integer initialSpendingTime; // xizmatga ketgan vaqt

    private Integer everyCount; //initial countdan keyingi qo'shimcha dokumentlarga ketadigan vaqt

    private Integer everySpendingTime; // har bir qoshimcha dokument

    private boolean dynamic;//dinamik true bo'ladi yuqoridagi holatda

    private boolean active;

    private Integer chargeMinute;

    private Double chargePercent;
}
