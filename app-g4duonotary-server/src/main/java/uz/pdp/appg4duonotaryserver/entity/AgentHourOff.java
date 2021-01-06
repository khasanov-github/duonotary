package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentHourOff extends AbsEntity {
    //Bu table bir agent ish vaqtini
    // aniq bir sanada aniq bir soat oralig'ni
    // dam olish qilish .
    @ManyToOne
    private User agent;

    private Timestamp fromTime;

    private Timestamp tillTime;


}
