package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainServiceWorkTimeDto {
    private UUID id;

    private UUID mainServiceId;

    private MainServiceDto mainServiceDto;

    private LocalTime fromTime; // Qo'shimcha ish vaqtlarini boshlanish vaqti

    private LocalTime tillTime; // Qo'shimcha ish vaqtlarini tugash vaqti

    private Double percent; //qo'shimcha foiz uchun

    private boolean active;  //qo'shimcha xizmat bor yoki yo'qligi

    private boolean online;

    private Double minPercent;

    private Double maxPercent;

    private boolean allZipCodes;

    private ZipCodeDto zipCodeDto;

    private List<UUID> stateIds;

    private List<UUID> countyIds;

    private List<UUID> zipCodeIds;

    public MainServiceWorkTimeDto(UUID id, MainServiceDto mainServiceDto, LocalTime fromTime, LocalTime tillTime, Double percent, boolean online, boolean active, ZipCodeDto zipCodeDto) {
        this.id = id;
        this.mainServiceDto = mainServiceDto;
        this.fromTime = fromTime;
        this.tillTime = tillTime;
        this.percent = percent;
        this.active = active;
        this.online = online;
        this.zipCodeDto = zipCodeDto;
    }
}
