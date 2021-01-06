package uz.pdp.appg4duonotaryserver.service.team_2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.Feedback;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.FeedbackDto;
import uz.pdp.appg4duonotaryserver.repository.FeedBackRepository;
import uz.pdp.appg4duonotaryserver.repository.OrderRepository;
import uz.pdp.appg4duonotaryserver.repository.UserRepository;
import uz.pdp.appg4duonotaryserver.service.MailService;
import uz.pdp.appg4duonotaryserver.utils.AppConstants;

@Service
public class FeedBackService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MailService mailService;
    @Autowired
    FeedBackRepository feedBackRepository;
    @Autowired
    UserRepository userRepository;

    public ApiResponse sentFeedBack(FeedbackDto feedbackDto, User user) {
        try {
            Feedback feedback = makeFeedBack(feedbackDto, new Feedback());
            if (feedbackDto.getRate() < AppConstants.NORMAl_RATE) {
                feedback.setText(feedbackDto.getText());
                mailService.feedbackToAdminEmail(feedbackDto,user.getEmail());
            }
            feedback.setRate(feedbackDto.getRate());

            feedBackRepository.save(feedback);
            return new ApiResponse("sent", true);
        } catch (Exception e) {
            return new ApiResponse("Error",false);
        }
    }

    public Feedback makeFeedBack(FeedbackDto feedbackDto, Feedback feedBack) {
        feedBack.setOrder(orderRepository.findById(feedbackDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("getOrder", "id", feedbackDto.getId())));
        feedBack.setUser(userRepository.findById(feedbackDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("getUser", "id", feedbackDto.getId())));
        feedBack.setAgent(feedbackDto.isAgent());
        feedBack.setRate(feedbackDto.getRate());
        feedBack.setText(feedbackDto.getText());
        return feedBack;
    }
}