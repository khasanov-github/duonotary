package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"fromTime", "main_service_id"}),
//        @UniqueConstraint(columnNames = {"tillTime", "main_service_id"})})
public class MainServiceWorkTime extends AbsEntity {
    //Agentni ishdan keyingi qoshimcha ish vaqtlari

    @ManyToOne(optional = false)
    private MainService mainService;

    private LocalTime fromTime; // Qo'shimcha ish vaqtlarini boshlanish vaqti

    private LocalTime tillTime; // Qo'shimcha ish vaqtlarini tugash vaqti

    private Double percent; //qo'shimcha foiz uchun

    private boolean active;  //qo'shimcha xizmat bor yoki yo'qligi

    @ManyToOne
    private ZipCode zipCode;



}
