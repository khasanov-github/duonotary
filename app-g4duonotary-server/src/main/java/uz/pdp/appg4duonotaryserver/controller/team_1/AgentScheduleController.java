package uz.pdp.appg4duonotaryserver.controller.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.payload.AgentScheduleDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.security.CurrentUser;
import uz.pdp.appg4duonotaryserver.service.AgentService;
import uz.pdp.appg4duonotaryserver.service.team_1.AgentScheduleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agentSchedule")
public class AgentScheduleController {

    @Autowired
    AgentScheduleService agentScheduleService;


    @PostMapping
    public HttpEntity<?> addSchedule(@CurrentUser User user,@RequestBody List<AgentScheduleDto> agentScheduleList) {
        ApiResponse apiResponse = agentScheduleService.addAgentSchedule(agentScheduleList,user);
        return ResponseEntity.status(apiResponse.isSuccess() ?  201 : 409).body(apiResponse);
    }

    @GetMapping("/byAgent/{agentId}")
    public HttpEntity<?> getSchedule(@PathVariable UUID agentId, @CurrentUser User user,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        ApiResponse apiResponse = agentScheduleService.getPageScheduleByAgent(agentId, user, page, size);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }





}
