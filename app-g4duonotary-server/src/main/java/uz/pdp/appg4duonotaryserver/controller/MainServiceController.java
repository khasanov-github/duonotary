package uz.pdp.appg4duonotaryserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.MainServiceDto;
import uz.pdp.appg4duonotaryserver.service.MainServiceService;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

import java.util.UUID;

@RestController
@RequestMapping("/api/mainService")
public class MainServiceController {

    @Autowired
    MainServiceService mainServiceService;

    @PostMapping
    public HttpEntity<?> saveOrEditMainService(@RequestBody MainServiceDto dto) {
        ApiResponse apiResponse = mainServiceService.saveAndEditMainService(dto);
            return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage().equals("Successfully Saved") ? 201 : 202 : 409).body(apiResponse);
    }

    @GetMapping
    public HttpEntity<?> getMainService(@RequestParam UUID id) {
        ApiResponse apiResponse = mainServiceService.getMainService(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/list")
    public HttpEntity<?> getMainServiceList(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return ResponseEntity.ok(mainServiceService.getMainServiceList(page, size));
    }

    @DeleteMapping("/remove/{id}")
    public HttpEntity<?> deleteMainService(@PathVariable UUID id) {
        ApiResponse apiResponse = mainServiceService.deleteMainService(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
