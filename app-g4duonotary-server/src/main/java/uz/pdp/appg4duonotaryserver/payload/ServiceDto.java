package uz.pdp.appg4duonotaryserver.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDto {
    private UUID id;

    private String name;

    private UUID mainServiceId;

    private Integer initialCount;

    private Integer initialSpendingTime;

    private Integer everyCount;

    private Integer everySpendingTime;

    private boolean dynamic;

    private boolean active;

    private Integer chargeMinute;

    private Double chargePercent;

    public ServiceDto(UUID id, String name, UUID mainServiceId, Integer initialCount,
                      Integer initialSpendingTime, Integer everyCount, Integer everySpendingTime,
                      boolean dynamic, boolean active, Integer chargeMinute, Double chargePercent) {
        this.id = id;
        this.name = name;
        this.mainServiceId = mainServiceId;
        this.initialCount = initialCount;
        this.initialSpendingTime = initialSpendingTime;
        this.everyCount = everyCount;
        this.everySpendingTime = everySpendingTime;
        this.dynamic = dynamic;
        this.active = active;
        this.chargeMinute = chargeMinute;
        this.chargePercent = chargePercent;
    }
}
