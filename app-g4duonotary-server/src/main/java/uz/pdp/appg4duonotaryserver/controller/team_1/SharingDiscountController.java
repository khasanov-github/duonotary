package uz.pdp.appg4duonotaryserver.controller.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.SharingDiscountTariffDto;
import uz.pdp.appg4duonotaryserver.service.team_1.SharingDiscountService;

@RestController
@RequestMapping("/api/sharingDiscount")
public class SharingDiscountController {

    @Autowired
    SharingDiscountService sharingDiscountService;

    @PostMapping
    public HttpEntity<?> saveOrEditSharingDiscTariff(@RequestBody SharingDiscountTariffDto sharingDiscountTariffDto) {
        ApiResponse apiResponse = sharingDiscountService.addOrEditSharingDiscTar(sharingDiscountTariffDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

//    @GetMapping("/getTariffPage")
//    public HttpEntity<?> getTariffPage(@RequestParam(value = "page", defaultValue = "0") int page,
//                                        @RequestParam(value = "size", defaultValue = "10") int size) {
//        ApiResponse apiResponse = sharingDiscountService.getPage(page, size);
//        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
//    }

    @GetMapping("/getGivenPage")
    public HttpEntity<?> getGivenPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        ApiResponse apiResponse = sharingDiscountService.getGivenPage(page, size);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping()
    public HttpEntity<?> getDtoList(@RequestParam(value = "search", defaultValue = "all") String search) {
        ApiResponse apiResponse = sharingDiscountService.getMinMaxList(search);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
