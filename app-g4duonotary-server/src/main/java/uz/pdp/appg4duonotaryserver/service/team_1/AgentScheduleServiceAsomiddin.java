package uz.pdp.appg4duonotaryserver.service.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.AgentSchedule;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.AgentScheduleDtoAsomidddin;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.repository.AgentScheduleRepository;

@Service
public class AgentScheduleServiceAsomiddin {
    @Autowired
    AgentScheduleRepository agentScheduleRepository;

    public ApiResponse saveOrEditAgantSchedule(AgentScheduleDtoAsomidddin agentScheduleDtoAsomidddin) {
        try {
            AgentSchedule agentSchedule = new AgentSchedule();
            if (agentScheduleDtoAsomidddin.getId() != null) {
                agentSchedule = agentScheduleRepository.findById(agentScheduleDtoAsomidddin.getId()).orElseThrow(() -> new ResourceNotFoundException("getAgentSheduleId", "Id", agentScheduleDtoAsomidddin.getId()));
            }
            agentSchedule.setUser(agentSchedule.getUser());
            agentSchedule.setHours(agentSchedule.getHours());
            agentSchedule.setWeekDay(agentSchedule.getWeekDay());
            agentSchedule.isDayOff();
            agentScheduleRepository.save(agentSchedule);
            return new ApiResponse(agentScheduleDtoAsomidddin.getId() != null ? "Edit" : "Save", true);
        } catch (Exception e) {
            return new ApiResponse("Error", false);
        }
    }
}
