package uz.pdp.appg4duonotaryserver.controller.team_1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.CountryDto;
import uz.pdp.appg4duonotaryserver.service.team_1.CountryService;

import java.util.UUID;

@RestController
@RequestMapping("/api/country")
public class CountryController {
    @Autowired
    CountryService countryService;


    @PostMapping
    public HttpEntity<?> saveOrEditCountry(@RequestBody CountryDto countryDto) {
        ApiResponse apiResponse = countryService.saveOrEditCountry(countryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getCountry(@PathVariable UUID id) {
        ApiResponse apiResponse = countryService.getCountry(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getCountryPage")
    public HttpEntity<?> getCountryPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size) {
        ApiResponse apiResponse = countryService.getCountryPage(page, size);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/remove")
    public HttpEntity<?> deleteCountryById(@RequestParam UUID id) {
        ApiResponse apiResponse = countryService.deleteCountry(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
