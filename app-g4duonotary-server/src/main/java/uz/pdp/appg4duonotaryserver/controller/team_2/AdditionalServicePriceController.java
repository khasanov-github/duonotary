package uz.pdp.appg4duonotaryserver.controller.team_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.AdditionalServicePriceDto;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.service.team_2.AdditionalServicePriceService;

import java.util.UUID;

@RestController
@RequestMapping("/api/additionalServicePrice")
public class AdditionalServicePriceController {
    @Autowired
    AdditionalServicePriceService additionalServicePriceService;

    @PostMapping
    HttpEntity<?> addAdditionalServicePrice(@RequestBody AdditionalServicePriceDto additionalServicePriceDto) {
        ApiResponse apiResponse = additionalServicePriceService.saveOrEditAdditionalServicePrice(additionalServicePriceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage().equals("Saved") ? 201 : 202 : 409).body(apiResponse);
    }


//    @GetMapping
//    HttpEntity<?> getAdditionalServicePricePage(@RequestParam(value = "page", defaultValue = "0") int page,
//                                                @RequestParam(value = "size", defaultValue = "10") int size) {
//        ApiResponse apiResponse = additionalServicePriceService.getAdditionalServicePricePage(page, size);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
//    }

    @GetMapping("/list")
    HttpEntity<?> additionalServicePriceList() {
        return ResponseEntity.ok(additionalServicePriceService.getAdditionalServicePriceDtoList());
    }


    @GetMapping("/delete")
    HttpEntity<?> deleteAdditionalServicePrice(@RequestParam UUID id) {
        ApiResponse apiResponse = additionalServicePriceService.deleteAdditionalServicePrice(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
