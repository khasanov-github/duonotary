package uz.pdp.appg4duonotaryserver.controller.team_2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.MainServiceWorkTimeDto;
import uz.pdp.appg4duonotaryserver.service.team_2.MainServiceWorkTimeService;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

import java.util.UUID;

@RestController
@RequestMapping("/api/mainServiceWorkTime")
public class MainServiceWorkTimeController {

    @Autowired
    MainServiceWorkTimeService mainServiceWorkTimeService;

    @PostMapping
    public HttpEntity<?> addMainServiceWorkTime(@RequestBody MainServiceWorkTimeDto dto) {
        ApiResponse apiResponse = mainServiceWorkTimeService.addAndEditMainServiceWorkTime(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage().equals("Saved") ? 201 : 202 : 409).body(apiResponse);
    }

//    @PutMapping("/editMainServiceWorkTime")
//    public HttpEntity<?> editMainServiceWorkTime(@RequestBody MainServiceWorkTimeDto dto) {
//        ApiResponse apiResponse = mainServiceWorkTimeService.addAndEditMainServiceWorkTime(dto);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
//    }

    @GetMapping
    public HttpEntity<?> getMainServiceworkTimePage(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(mainServiceWorkTimeService.getPageable(page, size));
    }

    @GetMapping("/minMaxPercent")
    public HttpEntity<?> getMinMaxPercent(@RequestParam(value = "search",defaultValue = "all") String search){
         ApiResponse apiResponse = mainServiceWorkTimeService.getMinMaxPercent(search);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/changeActive/{id}")
    public HttpEntity<?> changeActiveAdditionalServicePrice(@PathVariable UUID id) {
        ApiResponse apiResponse = mainServiceWorkTimeService.changeActive(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/remove")
    public HttpEntity<?> deleteMainServiceWorkTime(@PathVariable UUID id) {
        ApiResponse apiResponse = mainServiceWorkTimeService.deleteMainServiceWorkTime(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/byState")
    public HttpEntity<?> getCountiesByStateAndServiceId(
            @RequestParam(name = "stateId") UUID stateId) {
        return ResponseEntity.ok(mainServiceWorkTimeService.getCountiesByState(stateId));
    }

    @GetMapping("/byCounty")
    public HttpEntity<?> getZipCodesByServiceIdAndCountyId(
            @RequestParam(name = "countyId") UUID countyId) {
        return ResponseEntity.ok(mainServiceWorkTimeService.getZipCodesByCountyId(countyId));
    }
}
