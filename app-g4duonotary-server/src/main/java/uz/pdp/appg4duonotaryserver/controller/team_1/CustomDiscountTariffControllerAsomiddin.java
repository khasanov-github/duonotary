package uz.pdp.appg4duonotaryserver.controller.team_1;

import org.picocontainer.visitors.AbstractPicoVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.CustomDiscountTariffDtoAsomiddin;
import uz.pdp.appg4duonotaryserver.service.team_1.CustomDiscountTariffServiceAsomiddin;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

@Controller
@RequestMapping("/api/customDiscountTariff")
public class CustomDiscountTariffControllerAsomiddin {
    @Autowired
    CustomDiscountTariffServiceAsomiddin customDiscountTariffServiceAsomiddin;

    @PostMapping
    public HttpEntity<?> saveOraEditCustomDiscountTariff(@RequestBody CustomDiscountTariffDtoAsomiddin customDiscountTariffDtoAsomiddin) {
        ApiResponse apiResponse = customDiscountTariffServiceAsomiddin.saveOrEditCustomDisvountTariff(customDiscountTariffDtoAsomiddin);
        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage().equals("Saved") ? 201 : 202 : 409).body(apiResponse);
    }

    @GetMapping("/allByPageable")
    public HttpEntity<?> getBlogList(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return ResponseEntity.ok(customDiscountTariffServiceAsomiddin.getCustomDiscountTariffList(page, size));
    }
}
