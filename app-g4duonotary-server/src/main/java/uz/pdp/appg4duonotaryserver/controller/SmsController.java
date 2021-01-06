package uz.pdp.appg4duonotaryserver.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.service.SmsService;

@RestController
@RequestMapping("/api/smsVerify")
public class SmsController {
    final
    SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @GetMapping("/send")
    public HttpEntity<?> sendSms(@RequestParam String phoneNumber){
        ApiResponse apiResponse=smsService.sendSms(phoneNumber);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


    @GetMapping("/verify")
    public HttpEntity<?> verifySms(@RequestParam String phoneNumber,
                                   @RequestParam String code){
        ApiResponse apiResponse=smsService.verifySms(phoneNumber,code);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
