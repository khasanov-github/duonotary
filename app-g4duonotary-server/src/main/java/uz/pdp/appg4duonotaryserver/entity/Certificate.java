package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.enums.StatusEnum;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Certificate extends AbsEntity {

    @ManyToOne(optional = false)
    private User user;

    @OneToOne(optional = false)
    private Attachment attachment;

    @Column(nullable = false)
    private Date issueDate;

    @Column(nullable = false)
    private Date expireDate;

    @ManyToOne(optional = false)
    private  State state;//qaysi tuman uchun

    @Enumerated(EnumType.STRING)
    private StatusEnum statusEnum;//hujjatni admin tasdiqlaganligi holati

    private boolean expired;//muddati o'tganmi?


}
