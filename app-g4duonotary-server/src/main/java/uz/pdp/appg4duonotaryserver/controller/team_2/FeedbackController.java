package uz.pdp.appg4duonotaryserver.controller.team_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.FeedbackDto;
import uz.pdp.appg4duonotaryserver.security.CurrentUser;
import uz.pdp.appg4duonotaryserver.service.team_2.FeedBackService;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    FeedBackService feedBackService;

    @PostMapping
    HttpEntity<?> sentFeedBack(@RequestBody FeedbackDto feedbackDto, @CurrentUser User user) {
        ApiResponse apiResponse = feedBackService.sentFeedBack(feedbackDto,user);
         return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }
}