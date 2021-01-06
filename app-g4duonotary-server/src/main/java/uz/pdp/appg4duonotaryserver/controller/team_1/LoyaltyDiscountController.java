package uz.pdp.appg4duonotaryserver.controller.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appg4duonotaryserver.service.team_1.LoyaltyDiscountGivenService;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

@RestController
@RequestMapping("/api/loyaltyDiscountGiven")
public class LoyaltyDiscountController {

    @Autowired
    LoyaltyDiscountGivenService loyaltyDiscountGivenService;

    @GetMapping
    public HttpEntity<?> getLoyaltyDiscount(@RequestParam (value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER)Integer page,
                                            @RequestParam (value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE)Integer size){
        return ResponseEntity.ok(loyaltyDiscountGivenService.getLoyaltyDiscountGiven(page,size));
    }

}
