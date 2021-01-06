package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentBookedDto {

    private String fromTime;

    private Date date;

    private UUID servicePriceId;

    private Integer count;

    private String  zipCodeCode;

}
