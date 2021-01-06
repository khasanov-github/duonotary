package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.Attachment;
import uz.pdp.appg4duonotaryserver.entity.enums.StatusEnum;

import java.util.Date;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CertificateDto {

    private UUID id;
    private UUID attachmentId;
    private UserDto userDto;
    private UUID stateId;
    private Date issueDate;
    private Date expireDate;
    private StatusEnum statusEnum;
    private boolean expired;
}
