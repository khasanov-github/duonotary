package uz.pdp.appg4duonotaryserver.service.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.AgentSchedule;
import uz.pdp.appg4duonotaryserver.entity.Hour;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.payload.AgentScheduleDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.HourDto;
import uz.pdp.appg4duonotaryserver.repository.AgentScheduleRepository;
import uz.pdp.appg4duonotaryserver.repository.HourRepository;
import uz.pdp.appg4duonotaryserver.repository.UserRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgentScheduleService {
    @Autowired
    AgentScheduleRepository agentScheduleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HourRepository hourRepository;


    public ApiResponse addAgentSchedule(List<AgentScheduleDto> dtoList, User user) {
        try {
            for (AgentScheduleDto agentScheduleDto : dtoList) {
                AgentSchedule newSchedule = new AgentSchedule();
                newSchedule.setWeekDay(agentScheduleDto.getDay());
                newSchedule.setDayOff(agentScheduleDto.isDayoff());
                newSchedule.setUser(user);
                AgentSchedule saved = agentScheduleRepository.save(newSchedule);
                if (!agentScheduleDto.isDayoff()){
                    boolean check = checkHourDto(agentScheduleDto.getHourList());
                    if (check) {
                        for (HourDto hourDto : agentScheduleDto.getHourList()) {
                            hourRepository.save( new Hour(hourDto.getFromTime(), hourDto.getTillTime(), saved));
                        }
                    } else{
                        agentScheduleRepository.delete(saved);
                        return new ApiResponse("checking fail", false);
                    }
                }
            }
            return new ApiResponse("Agent schedule saved", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }

    public boolean checkHourDto(List<HourDto> hourList) {
        for (HourDto hourDto : hourList) {
            if (!hourDto.getFromTime().isBefore(hourDto.getTillTime())) {
                return false;
            } else {
                for (HourDto hourDto2 : hourList) {
                    if (hourDto.getFromTime() == hourDto2.getFromTime() && hourDto.getTillTime() == hourDto2.getTillTime()) {

                    } else {
                        if (hourDto2.getFromTime().isAfter(hourDto.getFromTime()) && hourDto2.getFromTime().isBefore(hourDto.getTillTime()) ||
                                hourDto2.getFromTime().isBefore(hourDto.getFromTime()) && hourDto2.getTillTime().isAfter(hourDto.getTillTime()) ||
                                hourDto2.getTillTime().isAfter(hourDto.getFromTime()) && hourDto2.getTillTime().isBefore(hourDto.getTillTime())) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public ApiResponse getPageScheduleByAgent(UUID userId, User user, int page, int size) {
        try {
            Page<AgentSchedule> all = agentScheduleRepository.findAllByUserId(userId != null ? userId : user.getId(), CommonUtils.getPageable(page, size));
            return new ApiResponse("success", true, all.getContent().stream().map(this::getDto).collect(Collectors.toList()), page, all.getTotalElements());
        } catch (IllegalArgumentException e) {
            return new ApiResponse("Error", false);
        }
    }

    public AgentScheduleDto getDto(AgentSchedule agentSchedule) {
        return new AgentScheduleDto(
                agentSchedule.getId(),
                agentSchedule.getWeekDay(),
                getHourDtoList(agentSchedule.getId()),
                agentSchedule.isDayOff());
    }

    public HourDto getHourDto(Hour hour){
        return new HourDto(
                hour.getId(),
                hour.getFromTime(),
                hour.getTillTime());
    }

    public List<HourDto> getHourDtoList(UUID agentScheduleId) {
        return hourRepository.findAllByAgentSchedule_Id(agentScheduleId).stream().map(this::getHourDto).collect(Collectors.toList());
    }

}
