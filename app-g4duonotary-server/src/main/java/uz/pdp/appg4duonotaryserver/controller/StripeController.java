package uz.pdp.appg4duonotaryserver.controller;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.service.StripeService;

import java.util.UUID;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin
public class StripeController {

    @Autowired
    StripeService stripeService;

    @GetMapping("/getClientSecretForNowCharge")
    public HttpEntity<?> getClientSecretForNowCharge(@RequestParam UUID orderId){
        ApiResponse apiResponse=stripeService.getClientSecretForNowCharge(orderId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/test")
    public HttpEntity<?> testCharge(){
        ApiResponse apiResponse=stripeService.testCharge();
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
