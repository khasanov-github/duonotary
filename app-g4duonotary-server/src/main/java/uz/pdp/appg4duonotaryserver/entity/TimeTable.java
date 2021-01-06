package uz.pdp.appg4duonotaryserver.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TimeTable extends AbsEntity{
    // BU CLASS ENDI AGENT BAND QILINSAGINA YOZILADI

    @ManyToOne(optional = false)
    private User agent;

    @Column(nullable = false)
    private Timestamp fromTime; // BUYURTMANING BOSHLANISH VAQT //10:00

    @Column(nullable = false)
    private Timestamp tillTime; // BUYURTMANING TUGASH VAQT //12:00

    @OneToOne
    private Order order;

    private boolean tempBooked; // VAQTINCHA BAND QLINDIMI ?
    private boolean booked; // VAQTINCHA BAND QLINDIMI ? ...

}
