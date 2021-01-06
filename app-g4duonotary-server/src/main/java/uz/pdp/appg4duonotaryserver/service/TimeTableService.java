package uz.pdp.appg4duonotaryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.*;
import uz.pdp.appg4duonotaryserver.payload.AgentBookedDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.FreeTimeDto;
import uz.pdp.appg4duonotaryserver.repository.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TimeTableService {

    @Autowired
    AgentScheduleRepository agentScheduleRepository;

    @Autowired
    AgentHourOffRepository agentHourOffRepository;

    @Autowired
    ServicePriceRepository servicePriceRepository;

    @Autowired
    MainServiceRepository mainServiceRepository;

    @Autowired
    TimeDurationRepository timeDurationRepository;

    @Autowired
    TimeTableRepository timeTableRepository;

    @Autowired
    HourRepository hourRepository;

    @Autowired
    UserRepository userRepository;


    public List<FreeTimeDto> getTimeTableToOrder(Date date, UUID servicePriceId, Integer count) {
        try {
            ServicePrice servicePrice = servicePriceRepository.findByIdAndActiveTrue(servicePriceId).orElseThrow(() -> new ResourceNotFoundException("servicePriceId"));
            Optional<MainService> byActiveTrueAndOnlineIsFalse = mainServiceRepository.findByActiveTrueAndOnlineFalse();
            if (byActiveTrueAndOnlineIsFalse.isPresent()) {

                MainService mainService = byActiveTrueAndOnlineIsFalse.get();
                LocalTime fromTime = mainService.getFromTime();
                LocalTime tillTime = mainService.getTillTime();

                List<FreeTimeDto> freeTime = new ArrayList<>();

                List<LocalTime> dividedTime = getDurationTime(fromTime, tillTime, timeDurationRepository.getLastDurationTime().getDurationTime());

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(date);

                List<AgentSchedule> byDateAndZipcode = agentScheduleRepository.getAgentScheduleBySelectedDate(strDate, servicePriceId);

                List<AgentHourOff> agentHourOffs = agentHourOffRepository.getAgentHourOffBySelectedDate(date);

                Integer totalSpendingTime = getTotalSpendingTime(count, servicePriceId);

                if (!strDate.equals(dateFormat.format(new Date()))) {
                    for (AgentSchedule agentSchedule : byDateAndZipcode) {
                        List<Hour> hours = hourRepository.findAllByAgentSchedule_Id(agentSchedule.getId());
                        for (Hour hour : hours) {
                            for (LocalTime time : dividedTime) {

                                LocalTime plusTime = time.plusMinutes(totalSpendingTime);

                                boolean checkPlusTime = time.isAfter(hour.getFromTime()) || time.equals(hour.getFromTime()) && plusTime.isBefore(hour.getTillTime());

                                boolean notHourOff = isNotHourOff(agentHourOffs, time, plusTime, agentSchedule.getUser().getId());

                                DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");
                                String amPm = time.format(format);
                                if (checkPlusTime && !notHourOff) {
                                    if (time.isBefore(hour.getTillTime().minusMinutes(totalSpendingTime))) {

                                        freeTime.add(new FreeTimeDto(time, amPm,true));
                                    }

                                }else{

                                    freeTime.add(new FreeTimeDto(time, amPm,false));
                                }
                            }
                        }
                    }
                }
                else {

                    for (AgentSchedule agentSchedule : byDateAndZipcode) {
                        List<Hour> hours = hourRepository.findAllByAgentSchedule_Id(agentSchedule.getId());
                        for (Hour hour : hours) {
                            for (LocalTime time : dividedTime) {

                                LocalTime plusTime = time.plusMinutes(totalSpendingTime);

                                boolean checkPlusTime = time.isAfter(hour.getFromTime()) || time.equals(hour.getFromTime()) && plusTime.isBefore(hour.getTillTime());

                                boolean notHourOff = isNotHourOff(agentHourOffs, time, plusTime, agentSchedule.getUser().getId());

                                DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");
                                String amPm = time.format(format);
                                if (checkPlusTime && !notHourOff) {
                                    if (time.isBefore(hour.getTillTime().minusMinutes(totalSpendingTime))) {

                                        freeTime.add(new FreeTimeDto(time, amPm,true));
                                    }

                                }else{

                                    freeTime.add(new FreeTimeDto(time, amPm,false));
                                }
                            }
                        }
                    }
                }
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isNotHourOff(List<AgentHourOff> agentHourOffs, LocalTime time, LocalTime plusMinutes, UUID agentId) {
        boolean isOff = false;
        for (AgentHourOff hourOff : agentHourOffs) {
            if (hourOff.getAgent().getId() == agentId) {
                Time from = new Time(hourOff.getFromTime().getTime());
                Time till = new Time(hourOff.getTillTime().getTime());
                isOff =
                        (time.isAfter(from.toLocalTime()) || time.equals(from.toLocalTime())) && plusMinutes.isBefore(till.toLocalTime()) ||
                                (time.isAfter(from.toLocalTime()) || time.equals(from.toLocalTime())) && time.isBefore(till.toLocalTime()) ||
                                plusMinutes.isAfter(from.toLocalTime()) && plusMinutes.isBefore(till.toLocalTime());
            }
            if (isOff) return isOff;
        }

        return isOff;
    }

    public Integer getTotalSpendingTime(Integer count, UUID servicePriceId) {
        ServicePrice servicePrice = servicePriceRepository.findByIdAndActiveTrue(servicePriceId).orElseThrow(() -> new ResourceNotFoundException("servicePriceId"));
        Integer initialCount = servicePrice.getService().getInitialCount();
        Integer initialSpendingTime = servicePrice.getService().getInitialSpendingTime();
        Integer everyCount = servicePrice.getService().getEveryCount();
        Integer everySpendingTime = servicePrice.getService().getEverySpendingTime();
        if (count > initialCount) {
            if ((count - initialCount) % everyCount != 0) {
                return (((count - initialCount) / everyCount) + 1) * everySpendingTime + initialSpendingTime;
            } else {
                return ((count - initialCount) / everyCount) * everySpendingTime + initialSpendingTime;
            }
        }
        return initialSpendingTime;
    }


    public List<LocalTime> getDurationTime(LocalTime fromTime, LocalTime tillTime, int duration) {
        List<LocalTime> getTimes = new ArrayList<>();
        while (fromTime != tillTime) {
            getTimes.add(fromTime);
            fromTime = fromTime.plusMinutes(duration);
        }
        return getTimes;
    }

    public ApiResponse tempBookedAgent(AgentBookedDto agentBookedDto){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = dateFormat.format(agentBookedDto.getDate());
            LocalDate localDate1 = LocalDate.parse(dateFormat.format(agentBookedDto.getDate()));
            LocalTime fromTime = LocalTime.parse(agentBookedDto.getFromTime());
            LocalTime tillTime = fromTime.plusMinutes(getTotalSpendingTime(agentBookedDto.getCount(), agentBookedDto.getServicePriceId()));
            Timestamp from_time = Timestamp.valueOf(LocalDateTime.of(localDate1, fromTime));
            Timestamp till_time = Timestamp.valueOf(LocalDateTime.of(localDate1, tillTime));
            List<Object[]> agent = timeTableRepository.getAgentFromDayOffFalseAndWeekDayAndFromTime(
                    from_time,
                    till_time,
                    date,
                    agentBookedDto.getZipCodeCode());
            for (Object[] objects : agent) {
                User user = userRepository.findById(UUID.fromString(objects[0].toString())).orElseThrow(() -> new uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException("getAgent", "getId", objects[0]));
                TimeTable timeTable=new TimeTable();
                timeTable.setAgent(user);
                timeTable.setTempBooked(true);
                timeTable.setFromTime(Timestamp.valueOf(LocalDateTime.of(localDate1, fromTime)));
                timeTable.setTillTime(Timestamp.valueOf(LocalDateTime.of(localDate1, tillTime)));
                timeTableRepository.save(timeTable);
            }
            return new ApiResponse("agent bant qilindi",true);
        }catch (Exception e){
            return new ApiResponse("error",false);
        }
    }

}
