package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;
import uz.pdp.appg4duonotaryserver.entity.template.AbsNameEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class County extends AbsNameEntity {
   @ManyToOne(optional = false)
   private State state;

}
