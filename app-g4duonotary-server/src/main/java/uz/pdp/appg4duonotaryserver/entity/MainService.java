package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsNameEntity;

import javax.persistence.Entity;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MainService extends AbsNameEntity {
    //BU TABLE PROJECT UCHUN INPERSON, ONLINE LARNI QAYD ETADI

    private LocalTime fromTime;//ISH VAQTINING BOSHLANISHI

    private LocalTime tillTime;//ISH VAQTINING TUGASHI

    private boolean online;//BU ONLINE YOKI IN-PRESON EKNALIGINI BILISH UCHUN

    private Integer orderNumber;//BU NICHINCHI BULIB KURINISHI ONLINE OR IN-PERSON

}
