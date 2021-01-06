package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RealEstate extends AbsEntity {
    //BU UY JOY OLDI-SOTDI SHARTNOMALARI QILISH
    // UCHUN TO'LDIRILADIGAN MA'LUMOTLAR

    private String requester;//agentstva nomi
    private String requesterPhone;//agentstva tel raqami
    private String requesterAddress;//agentstva MANZILI
    private String email;//agentsva emaili
    private String clientName;//clientni ismi
    private String clientPhone;//clientni tel raqami
    private String clientAddress;//clientni MANZILI
    private Date date;
    private Time time;
    private String message;
    @OneToOne(optional = false)
    private Order order;

}
