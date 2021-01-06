package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.Attachment;
import uz.pdp.appg4duonotaryserver.entity.ServicePrice;
import uz.pdp.appg4duonotaryserver.entity.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  OrderDto {
    private UUID id;

    private String address; //manzil

    private UUID clientId; //qaysi client

    private Float lan;//IN-PERSON CLIENTNING MAP ADDRESS

    private Float lat;//IN-PERSON CLIENTNING MAP ADDRESS

    private UUID servicePriceId;

    private UUID agentId;//qaysi agent hizmat qilishi

    private Double amount; //BUYURTMANING CHEGIRMALARSIZ SUMMASI

    private double amountDiscount;//BARCHA TURDAGI CHEGIRMALARNING DOLLARDAGI MIQDORI

    private String checkNumber;//STRIPENING CHEK RAQAMI

    private Integer serialNumber;//GENERATE QILINADIGAN RAQAM CLIENT UCHUN

    private OrderStatus orderStatus;//BUYURTMA HOLATI

    private Integer countDocument;//MIJOSNING NATARIUS QILMOQCHI BO`LGAN HUJJATLAR SONI

    private List<Attachment> documents;//Client tashagan HUJJATLARI (misol passport nusxalri)

    private List<Attachment> docVerifyDocuments;//docVerify documentlari ozimizda turadi, agar docVerify saytida ubu narsa bolsa bizda malumotlar qoladi

    private String docVerifyId; //DOC VERIFY TIZIMIDAGI YUKLANADIGAN HUJJATNING IDSI

    private boolean packet; //DOC VERIFY TIZIMIDAGI YUKLANADIGAN HUJJATNING TURI PACKET BO`LSA SHU FIELD TRUE BO`LADI VA DOC-VERIFYIDDA PACKET_ID

    public OrderDto(String address, Integer serialNumber) {
        this.address = address;
        this.serialNumber = serialNumber;
    }

    public OrderDto(String address, UUID clientId, Float lan, Float lat,
                    UUID servicePriceId, UUID agentId, Double amount,
                    double amountDiscount,
                    String checkNumber, Integer serialNumber,
                    OrderStatus orderStatus, Integer countDocument,
                    List<Attachment> documents, List<Attachment> docVerifyDocuments,
                    String docVerifyId, boolean packet) {
        this.address = address;
        this.clientId = clientId;
        this.lan = lan;
        this.lat = lat;
        this.servicePriceId= servicePriceId;
        this.agentId = agentId;
        this.amount = amount;
        this.amountDiscount = amountDiscount;
        this.checkNumber = checkNumber;
        this.serialNumber = serialNumber;
        this.orderStatus = orderStatus;
        this.countDocument = countDocument;
        this.documents = documents;
        this.docVerifyDocuments = docVerifyDocuments;
        this.docVerifyId = docVerifyId;
        this.packet = packet;
    }
}
