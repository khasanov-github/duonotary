package uz.pdp.appg4duonotaryserver.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import uz.pdp.appg4duonotaryserver.payload.UserDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Configuration configuration;

    public void send(UUID code, UserDto userDto, Boolean changing) throws MessagingException, IOException, TemplateException {
        Map<String, Object> model = new HashMap<>();
        model.put("email", userDto.getEmail());
        model.put("changing", changing.toString());
        model.put("code", code);
        Object fullName = model.put("fullName", userDto.getFirstName()+ " " + userDto.getLastName());
        sendMailSender(userDto.getEmail(),model,"email-template.ftl");


    }


    public void sendMailSender(String email, Map<String, Object> model, String htmlFile) throws MessagingException, IOException, TemplateException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name());

        Template t = configuration.getTemplate(htmlFile);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject("\uD83D\uDCDB noreply");
        javaMailSender.send(message);
    }


}
