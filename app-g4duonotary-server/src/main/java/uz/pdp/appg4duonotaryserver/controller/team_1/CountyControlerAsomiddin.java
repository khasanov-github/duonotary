package uz.pdp.appg4duonotaryserver.controller.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.CountryDto;
import uz.pdp.appg4duonotaryserver.payload.CountyDtoAsomiddin;
import uz.pdp.appg4duonotaryserver.service.team_1.CountyServiceAsomiddin;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

import java.util.UUID;

@RestController
@RequestMapping("/api/county")
public class CountyControlerAsomiddin {
    @Autowired
    CountyServiceAsomiddin countyServiceAsomiddin;

    @PostMapping
    public HttpEntity<?> saveOrEditCounty(@RequestBody CountyDtoAsomiddin countyDtoAsomiddin) {
        ApiResponse apiResponse = countyServiceAsomiddin.saveOrEditCounty(countyDtoAsomiddin);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage().equals("Saved") ? 201 : 202 : 409).body(apiResponse);
    }

    @GetMapping({"/id"})
    public HttpEntity<?> getCountyId(@PathVariable UUID id){
        ApiResponse apiResponse=countyServiceAsomiddin.getCountyId(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @GetMapping("/allByPageable")
    public HttpEntity<?> getBlogList(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return ResponseEntity.ok(countyServiceAsomiddin.getCountyList(page, size));
    }
    @GetMapping("/remove")
   public HttpEntity<?> deleteCountyAsomiddinId(@RequestParam UUID id){
        ApiResponse apiResponse=countyServiceAsomiddin.deleteCountyAsomiddin(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
