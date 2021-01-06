package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback extends AbsEntity {
   // Buyurmani baholiydi

   @ManyToOne
   private Order order;// qaysi zakazligi

   @ManyToOne
   private User user;// client yoki agent

   private boolean agent;//agentligini bilish

   private int rate;

   @Column(columnDefinition = "text")
   private String text;
}
