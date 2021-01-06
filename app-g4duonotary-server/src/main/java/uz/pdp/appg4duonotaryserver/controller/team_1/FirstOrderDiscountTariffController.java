package uz.pdp.appg4duonotaryserver.controller.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.FirstOrderDiscountTariffDto;
import uz.pdp.appg4duonotaryserver.service.team_1.FirstOrderDiscountTariffService;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

@RestController
@RequestMapping("/api/firstOrderDiscount")
public class FirstOrderDiscountTariffController {
    @Autowired
    FirstOrderDiscountTariffService firstOrderService;

    @PostMapping("/tariff")
    public HttpEntity<?> saveOrEditFirstOrder(@RequestBody FirstOrderDiscountTariffDto dto) {
        ApiResponse apiResponse = firstOrderService.saveFirstOrderDiscountTariff(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping("/minOrMax")
    public HttpEntity<?> minOrMaxFirstOrderDiscountTariff(@RequestBody String search) {
        return ResponseEntity.ok(firstOrderService.getMinMaxPercentFirstOrderDiscountTariff(search));
    }

    @GetMapping("/given")
    public HttpEntity<?> getFirstOrderDiscountGivenList
            (@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
             @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return ResponseEntity.ok(firstOrderService.getFirstOrderDiscountGivenList(page, size));
    }

}
