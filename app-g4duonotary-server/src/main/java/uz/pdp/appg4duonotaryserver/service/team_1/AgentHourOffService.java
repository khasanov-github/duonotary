package uz.pdp.appg4duonotaryserver.service.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.AgentHourOff;
import uz.pdp.appg4duonotaryserver.entity.TimeTable;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.AgentHourOffDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.repository.AgentHourOffRepository;
import uz.pdp.appg4duonotaryserver.repository.TimeTableRepository;
import uz.pdp.appg4duonotaryserver.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class AgentHourOffService {
    @Autowired
    AgentHourOffRepository hourOffRepository;

    @Autowired
    TimeTableRepository timeTableRepository;

    @Autowired
    UserRepository userRepository;

    public ApiResponse addHourOff(User user, AgentHourOffDto dto) {
        if (timeTableRepository.existsByAgentIdAndBookedTrue(user.getId())) {
            List<TimeTable> timeTables = timeTableRepository.findAllByAgentIdAndBookedTrue(user.getId());
            boolean ok=false;
            for (TimeTable timeTable : timeTables) {
                if (dto.getFromTime().before(timeTable.getFromTime()) && dto.getTillTime().before(timeTable.getFromTime()) ||
                        dto.getFromTime().after(timeTable.getTillTime())&&dto.getTillTime().after(timeTable.getTillTime())){
                    ok=true;
                }else
                    ok=false;
                    break;
            }
                if (ok){
                    AgentHourOff hourOff = new AgentHourOff();
                    hourOff.setAgent(userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("user", "user", user.getId())));
                    hourOff.setFromTime(dto.getFromTime());
                    hourOff.setTillTime(dto.getTillTime());
                    hourOffRepository.save(hourOff);
                    return new ApiResponse("saved", true);
                }else
                    return new ApiResponse("you have order", false);

        } else {
            AgentHourOff hourOff = new AgentHourOff();
            hourOff.setAgent(userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("user", "user", user.getId())));
            hourOff.setFromTime(dto.getFromTime());
            hourOff.setTillTime(dto.getTillTime());
            hourOffRepository.save(hourOff);
            return new ApiResponse("saved", true);
        }
    }

    public ApiResponse getHourOff(UUID agentId){
        try {
            List<AgentHourOff> all = hourOffRepository.findAllByAgentId(agentId);
            if (all.size()>0){
                return new ApiResponse("success",true,all);
            }
            return new ApiResponse("not hourOff ",true);
        }catch (Exception e){
            return new ApiResponse("error",false);
        }

    }

}
