package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.enums.OrderStatus;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order extends AbsEntity {
    //Buyurtmalar

    @Column(nullable = false)
    private String address; //manzil

    @ManyToOne(optional = false)
    private User client; //qaysi client

    private Float lan;//IN-PERSON CLIENTNING MAP ADDRESS

    private Float lat;//IN-PERSON CLIENTNING MAP ADDRESS

    @ManyToOne(optional = false)
    private ServicePrice servicePrice;

    @ManyToOne(optional = false)
    private User agent;//qaysi agent hizmat qilishi

    @Column(nullable = false)
    private Double amount; //BUYURTMANING CHEGIRMALARSIZ SUMMASI

    private double amountDiscount;//BARCHA TURDAGI CHEGIRMALARNING DOLLARDAGI MIQDORI

    private String checkNumber;//STRIPENING CHEK RAQAMI

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serialNumber;//GENERATE QILINADIGAN RAQAM CLIENT UCHUN

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;//BUYURTMA HOLATI

    @Column(nullable = false)
    private Integer countDocument;//MIJOSNING NATARIUS QILMOQCHI BO`LGAN HUJJATLAR SONI

    @OneToMany
    private List<Attachment> documents;//Client tashagan HUJJATLARI (misol passport nusxalri)

    @OneToMany
    private List<Attachment> docVerifyDocuments;//docVerify documentlari ozimizda turadi, agar docVerify saytida ubu narsa bolsa bizda malumotlar qoladi

    private String docVerifyId; //DOC VERIFY TIZIMIDAGI YUKLANADIGAN HUJJATNING ID si

    private boolean packet; //DOC VERIFY TIZIMIDAGI YUKLANADIGAN HUJJATNING TURI PACKET BO`LSA SHU FIELD TRUE BO`LADI VA DOC-VERIFYIDDA PACKET_ID


}
