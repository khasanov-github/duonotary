package uz.pdp.appg4duonotaryserver.controller.team_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.ServicePriceDto;
import uz.pdp.appg4duonotaryserver.service.team_2.MainServiceWorkTimeService;
import uz.pdp.appg4duonotaryserver.service.team_2.ServicePriceService;

import java.util.UUID;

@RestController
@RequestMapping("/api/servicePrice")
public class ServicePriceController {
    private final ServicePriceService servicePriceService;

    @Autowired
    MainServiceWorkTimeService mainServiceWorkTimeService;

    public ServicePriceController(ServicePriceService servicePriceService) {
        this.servicePriceService = servicePriceService;
    }


    @PostMapping
    public HttpEntity<?> saveOrEditServicePrice(@RequestBody ServicePriceDto servicePriceDto) {
        ApiResponse apiResponse = servicePriceService.saveOrEditServicePrice(servicePriceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/minOrMax")
    public HttpEntity<?> minOrMaxServicePrice() {
        return ResponseEntity.ok(servicePriceService.getServicePriceDtoList());
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
