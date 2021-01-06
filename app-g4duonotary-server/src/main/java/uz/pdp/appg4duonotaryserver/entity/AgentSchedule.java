package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.enums.WeekDay;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AgentSchedule extends AbsEntity {

    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;

    @OneToMany
    private List<Hour> hours;

    private boolean dayOff;

    @ManyToOne
    private User user;
}
