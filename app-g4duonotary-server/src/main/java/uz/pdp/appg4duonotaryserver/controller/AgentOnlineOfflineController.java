package uz.pdp.appg4duonotaryserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appg4duonotaryserver.payload.AgentOnlineOfflineDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.service.AgentOnlineOfflineService;

@RestController
@RequestMapping("/api/agentOnlineOffline")
public class AgentOnlineOfflineController {
    @Autowired
    AgentOnlineOfflineService agentOnlineOfflineService;

    @PostMapping
    public HttpEntity<?> addOnOff(@RequestBody AgentOnlineOfflineDto dto) {
        ApiResponse apiResponse = agentOnlineOfflineService.addOnOff(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
}
