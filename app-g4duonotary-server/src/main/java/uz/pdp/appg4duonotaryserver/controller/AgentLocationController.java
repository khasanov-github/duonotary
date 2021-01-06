package uz.pdp.appg4duonotaryserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appg4duonotaryserver.payload.AgentLocationDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.service.AgentLocationService;

@RestController
@RequestMapping("/api/agentLoc")
public class AgentLocationController {

    @Autowired
    AgentLocationService agentLocationService;


    @PostMapping
    public HttpEntity<?> addLocation(@RequestBody AgentLocationDto dto){
        ApiResponse apiResponse = agentLocationService.addLoc(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
}
