package uz.pdp.appg4duonotaryserver.controller.team_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.appg4duonotaryserver.service.team_1.CustomDiscountGivenServiceAsomiddin;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

@Controller
@RequestMapping("/api/customDiscountGiven")
public class CustomDiscountGivenControllerAsomiddin {
    @Autowired
    CustomDiscountGivenServiceAsomiddin customDiscountGivenServiceAsomiddin;

    @GetMapping("/allByPageable")
    public HttpEntity<?> getBlogList(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return ResponseEntity.ok(customDiscountGivenServiceAsomiddin.getCustomDiscountGivenList(page, size));
    }
}
