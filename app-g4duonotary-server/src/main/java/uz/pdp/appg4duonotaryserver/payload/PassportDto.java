package uz.pdp.appg4duonotaryserver.payload;

import lombok.Data;
import uz.pdp.appg4duonotaryserver.entity.Attachment;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.entity.enums.StatusEnum;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.UUID;

@Data
public class PassportDto {
    private UUID id;

    private UUID attachmentId;

    private Date issueDate;

    private Date expireDate;

    private StatusEnum statusEnum;//hujjatni admin tasdiqlaganligi holati

    private boolean expired;//muddati o'tganmi?
}
