package uz.pdp.appg4duonotaryserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.StateDto;
import uz.pdp.appg4duonotaryserver.repository.StateRepository;
import uz.pdp.appg4duonotaryserver.service.StateService;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

import java.util.UUID;

@Controller
@RequestMapping("/api/state")
public class StateController {
    @Autowired
    StateService stateService;
    @Autowired
    StateRepository stateRepository;

    @PostMapping
    public HttpEntity<?> saveOrEditState(@RequestBody StateDto stateDto) {
        ApiResponse apiResponse = stateService.saveOrEditState(stateDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping({"/id"})
    public HttpEntity<?> getStateId(@PathVariable UUID id){
        ApiResponse apiResponse=stateService.getStateId(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @GetMapping("/getStatePage")
    public HttpEntity<?> getBlogList(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return ResponseEntity.ok(stateService.getStatePage(page, size));
    }
    @GetMapping("/remove")
    public HttpEntity<?> deleteState(@RequestParam UUID id){
        ApiResponse apiResponse=stateService.deleteState(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
