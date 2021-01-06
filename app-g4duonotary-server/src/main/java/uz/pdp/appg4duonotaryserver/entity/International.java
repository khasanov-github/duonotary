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
import java.sql.Time;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class International extends AbsEntity {
    //BU XALQARO XUJJATLARNI TASDIQLASH UCHUN TO'LDIRILADIGAN MA'LUMOTLAR
    private boolean embassy; //XALQARO KONVENSIYAGA QO'SHILMAGAN DAVLAT UCHUN XUJJATMI

    private String documentType;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer documentCount;

    private Date date;

    private Time time;

    @Column(nullable = false)
    private String address;

    @OneToOne(optional = false)
    private Order order;

//    private String message;

    @ManyToOne(optional = false)
    private Country country;
}
