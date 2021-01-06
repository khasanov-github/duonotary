package uz.pdp.appg4duonotaryserver.controller.team_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.entity.OutOfService;
import uz.pdp.appg4duonotaryserver.payload.OutOfServiceDto;
import uz.pdp.appg4duonotaryserver.service.team_2.OutOfServiceService;

@RestController
@RequestMapping("/api/outOfService")
public class OutOfServiceController {

    @Autowired
    OutOfServiceService outOfServiceService;

    @PostMapping
    HttpEntity<?> sentEmail(@RequestBody OutOfServiceDto outOfServiceDto){
        outOfServiceService.sentEmail(outOfServiceDto);
        return null;
    }

}
