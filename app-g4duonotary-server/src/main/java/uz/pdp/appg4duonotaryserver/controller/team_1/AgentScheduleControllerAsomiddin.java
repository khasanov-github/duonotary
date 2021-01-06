package uz.pdp.appg4duonotaryserver.controller.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.appg4duonotaryserver.payload.AgentScheduleDtoAsomidddin;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.service.team_1.AgentScheduleServiceAsomiddin;

@Controller
@RequestMapping("/api/agentShedule")
public class AgentScheduleControllerAsomiddin {
    @Autowired
    AgentScheduleServiceAsomiddin agentScheduleServiceAsomiddin;

    @PostMapping
    public HttpEntity<?> saveOrEditAgantSchedule(@RequestBody AgentScheduleDtoAsomidddin agentSheduleDtoAsomidddin) {
        ApiResponse apiResponse = agentScheduleServiceAsomiddin.saveOrEditAgantSchedule(agentSheduleDtoAsomidddin);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage().equals("Saved") ? 201 : 202 : 409).body(apiResponse);
    }
}
