package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Hour extends AbsEntity {

    @Column(nullable = false)
    private LocalTime fromTime;//zakaz oliwwi bowlanishi

    @Column(nullable = false)
    private LocalTime tillTime;// zakazni olishni tugash vaqti

    @ManyToOne(fetch = FetchType.LAZY)
    private AgentSchedule agentSchedule;


}
