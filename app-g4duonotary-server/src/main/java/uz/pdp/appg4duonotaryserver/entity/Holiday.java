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
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Holiday extends AbsNameEntity {

    @ManyToOne(optional = false)
    private MainService mainService;

    @Column(nullable = false)
    private Date date;
}
