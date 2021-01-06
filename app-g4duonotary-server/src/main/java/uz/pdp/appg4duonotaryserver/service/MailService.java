package uz.pdp.appg4duonotaryserver.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uz.pdp.appg4duonotaryserver.entity.Feedback;
import uz.pdp.appg4duonotaryserver.payload.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;


    public boolean sendEmailWithHtml(String sendToEmail, Map<String, Object> model, String emailTemplate) {

        MimeMessage message = sender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template t = config.getTemplate(emailTemplate);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(sendToEmail);
            helper.setText(html, true);
            helper.setSubject("\uD83D\uDCDB noreply");
            sender.send(message);


            return true;

        } catch (MessagingException | IOException | TemplateException e) {
            return false;
        }


    }


    public ApiResponse sendVerificationToEmail(UserDto userDto, String emailCode, boolean changeEmail) {
        Map<String, Object> model = new HashMap<>();
        model.put("fullName", userDto.getFirstName() + " " + userDto.getLastName());
        model.put("emailCode", emailCode);
        model.put("changeEmail", changeEmail);
        boolean b = sendEmailWithHtml(changeEmail ? userDto.getChangedEmail() : userDto.getEmail(), model, "verification.ftl");

        return new ApiResponse(b ? "ok" : "error", b);
    }

    public ApiResponse forgetPassword(UserDto userDto, String forgetPasswordCode) {
        Map<String, Object> model = new HashMap<>();
        model.put("fullName", userDto.getFirstName() + " " + userDto.getLastName());
        model.put("userId", userDto.getId());
        model.put("forgetPasswordCode", forgetPasswordCode);
        boolean b = sendEmailWithHtml(userDto.getEmail(), model, "forgetPassword.ftl");

        return new ApiResponse(b ? "ok" : "error", b);
    }

    public ApiResponse outOfService(String email, String zipCodeCode) {
        Map<String, Object> model = new HashMap<>();
        model.put("code", zipCodeCode);

        boolean b = sendEmailWithHtml(email, model, "outOfService.ftl");

        return new ApiResponse(b ? "ok" : "error", b);
    }


    public ApiResponse feedbackToAdminEmail(FeedbackDto feedbackDto, String adminEmail) {
        Map<String, Object> model = new HashMap<>();
        model.put("orderNumber", feedbackDto.getOrderDto());
        model.put("orderAddress", feedbackDto.getOrderDto());
        model.put("owner", feedbackDto.isAgent() ? "agent" : "client");
//        model.put("phoneNumber", feedbackDto.getUserDto().getPhoneNumber());
//        model.put("email", feedbackDto.getUserDto().getEmail());
        model.put("rate", feedbackDto.getRate());
        model.put("text", feedbackDto.getText());
//        model.put("fullName", feedbackDto.getUserId() + " " + feedbackDto.getUserDto().getLastName());

        boolean b = sendEmailWithHtml(adminEmail, model, "feedbackToAdminEmail.ftl");

        return new ApiResponse(b ? "ok" : "error", b);
    }

    public ApiResponse agentInfoChange(CertificateDto dto, String description) {
        Map<String, Object> model = new HashMap<>();
       model.put("userFullName", dto.getUserDto().getFirstName() + " " + dto.getUserDto().getLastName());
        model.put("statusEnum",dto.getStatusEnum());
        model.put("description",description);

        boolean b = sendEmailWithHtml(dto.getUserDto().getEmail(), model, "send-status.ftl");

        return new ApiResponse(b ? "ok" : "error", b);
    }

    public ApiResponse orderNotification(LocalTime fromTime, String email, OrderDto orderDto) {
        Map<String, Object> model = new HashMap<>();
        model.put("fromTime", fromTime);
        model.put("orderNumber", orderDto.getSerialNumber());

        boolean b = sendEmailWithHtml(email, model, "orderNotification.ftl");

        return new ApiResponse(b ? "ok" : "error", b);
    }


}
