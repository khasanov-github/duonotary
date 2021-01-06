package uz.pdp.appg4duonotaryserver.service.team_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.Holiday;
import uz.pdp.appg4duonotaryserver.entity.MainService;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.HolidayDto;
import uz.pdp.appg4duonotaryserver.repository.HolidayRepository;
import uz.pdp.appg4duonotaryserver.repository.MainServiceRepository;
import uz.pdp.appg4duonotaryserver.repository.TimeTableRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HolidayService {
    @Autowired
    HolidayRepository holidayRepository;
    @Autowired
    MainServiceRepository mainServiceRepository;
    @Autowired
    TimeTableRepository timeTableRepository;


    public ApiResponse saveAndEditHoliday(HolidayDto holidayDto) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(holidayDto.getDate());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        Timestamp fromTimeBefore = new Timestamp(calendar.getTime().getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Timestamp fromTimeAfter = new Timestamp(calendar.getTime().getTime());
        if (timeTableRepository.existsAllByFromTimeAfterAndFromTimeBefore(fromTimeBefore, fromTimeAfter)) {
            return new ApiResponse("selected date is occupied", false);
        } else {
            Holiday holiday = new Holiday();
            if (holidayDto.getId() != null) {
                holiday = holidayRepository.findById(holidayDto.getId()).orElseThrow(() -> new ResourceNotFoundException("nothing found with given id", "holiday1", holidayDto.getId()));
                ;
            } else if (holidayRepository.existsByName(holidayDto.getName())) {
                return new ApiResponse("error! the given name is exists on database", false);
            }

            MainService mainServiceOptional = mainServiceRepository.findById(holidayDto.getMainServiceId()).orElseThrow(() -> new ResourceNotFoundException("nothing found with given id", "Mainservice", holidayDto.getId()));
            holiday.setDate(holidayDto.getDate());
            holiday.setMainService(mainServiceOptional);
            holiday.setActive(holidayDto.isActive());
            holiday.setName(holidayDto.getName());
            holiday.setDescription(holidayDto.getDescription());
            holidayRepository.save(holiday);
            return new ApiResponse(holiday.getId() == null ? "holiday modified" : "holiday saved", true);
        }
    }

    public ApiResponse deleteHoliday(HolidayDto holidayDto) {
        if (holidayDto.getId() != null && holidayRepository.existsById(holidayDto.getId())) {
            holidayRepository.deleteById(holidayDto.getId());
            return new ApiResponse("deleted succesfully!", true);
        } else {
            throw new ResourceNotFoundException("resource not found with given id", "holiday deletion", (Object) null);
        }
    }

    public ApiResponse getHolidaysList(int page, int size) {

        try {
            Page<Holiday> holidaylist = holidayRepository.findAll(CommonUtils.getPageable(page, size));
            return new ApiResponse("success", true,
                    holidaylist.getContent().stream().map(this::getHolidayDto).collect(Collectors.toList()), page, holidaylist.getTotalElements());
        } catch (IllegalArgumentException e) {
            return new ApiResponse("Error", false);
        }
    }

    public ApiResponse getHoliday(UUID uuid){
        Holiday holiday =  holidayRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("nothing found with given id","holiday_id",uuid));
        return new ApiResponse("success",true, getHolidayDto(holiday));
    }

    private HolidayDto getHolidayDto(Holiday holiday) {
        return new HolidayDto(
                holiday.getId(),
                holiday.getName(),
                holiday.isActive(),
                holiday.getDescription(),
                holiday.getMainService().getId(),
                holiday.getDate());
    }
}
