package uz.pdp.appg4duonotaryserver.service;

import com.twilio.Twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.SmsVerify;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.repository.SmsVerifyRepository;

import java.util.Optional;
import java.util.Random;

@Service
public class SmsService {
    @Autowired
    SmsVerifyRepository smsVerifyRepository;

    @Value("${twilio.token}")
    private String twilioToken;

    @Value("${twilio.sid}")
    private String twilioSid;


    public ApiResponse sendSms(String phoneNumber) {
        try {
            phoneNumber=phoneNumber.startsWith("+")?phoneNumber:"+"+phoneNumber;
            Twilio.init(twilioSid, twilioToken);
            String code = String.valueOf(generateCode());
            Message message = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber("+12569801262"),
                    code)
                    .create();
            Optional<SmsVerify> byPhoneNumber = smsVerifyRepository.findByPhoneNumber(phoneNumber);
            if (byPhoneNumber.isPresent()) {
                SmsVerify smsVerify = byPhoneNumber.get();
                smsVerify.setCode(code);
                smsVerify.setChecked(false);
                smsVerifyRepository.save(smsVerify);
            } else {
                SmsVerify smsVerify = new SmsVerify();
                smsVerify.setPhoneNumber(phoneNumber);
                smsVerify.setCode(code);
                smsVerifyRepository.save(smsVerify);
            }
            return new ApiResponse("ok ", true);
        } catch (Exception e) {
            return new ApiResponse("error ", false);
        }

    }

        public int generateCode() {
        return new Random().nextInt(900000) + 100000;
    }



    public ApiResponse verifySms(String phoneNumber, String code) {
        phoneNumber=phoneNumber.startsWith("+")?phoneNumber:"+"+phoneNumber;
        Optional<SmsVerify> byPhoneNumber = smsVerifyRepository.findByPhoneNumber(phoneNumber);
        if (byPhoneNumber.isPresent()) {
            SmsVerify smsVerify = byPhoneNumber.get();
            if (smsVerify.getCode().equals(code)){
                smsVerify.setChecked(true);
                smsVerifyRepository.save(smsVerify);
                return new ApiResponse("ok",true);
            }
            return new ApiResponse("error Code",false);
        }

        return new ApiResponse("error PhoneNumber",false);
    }

}
