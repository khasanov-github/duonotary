package uz.pdp.appg4duonotaryserver.controller;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.JwtToken;
import uz.pdp.appg4duonotaryserver.payload.ReqSignIn;
import uz.pdp.appg4duonotaryserver.payload.UserDto;
import uz.pdp.appg4duonotaryserver.security.CurrentUser;
import uz.pdp.appg4duonotaryserver.security.JwtTokenProvider;
import uz.pdp.appg4duonotaryserver.service.AuthService;
import uz.pdp.appg4duonotaryserver.service.MailService;
import uz.pdp.appg4duonotaryserver.service.UserService;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MailService mailService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody ReqSignIn reqSignIn) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(reqSignIn.getUserName(), reqSignIn.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(((User) authentication.getPrincipal()));
        return ResponseEntity.ok(new JwtToken(token));
    }

    @GetMapping("/me")
    public HttpEntity<?> userMe(@CurrentUser User user){
        return ResponseEntity.status(user!=null?200:409).body(user);
    }

    @PostMapping("/registerUser")
    public HttpEntity<?> registerUser(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = authService.registerUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PostMapping("/registerAgent")
    public HttpEntity<?> registerAgent(@RequestBody UserDto userDto) throws MessagingException, IOException, TemplateException {
        ApiResponse apiResponse = authService.registerAgent(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @GetMapping({"/verifyEmail"})
    public HttpEntity<?> verifyEmail(@RequestParam String emailCode, @RequestParam boolean isAccept, @RequestParam boolean changeEmail) {
        ApiResponse apiResponse = authService.verifyEmail(emailCode, isAccept, changeEmail);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/editPassword")
    public HttpEntity<?> editpassword(@RequestParam String changedPassword,
                                      @RequestParam String oldPassword,
                                      @CurrentUser User user) {
        ApiResponse apiResponse = userService.editPassword(changedPassword, oldPassword, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/forgetPasswordMailSender")
    public HttpEntity<?> forgetPasswordMailSender(@RequestParam String email) {
        ApiResponse apiResponse = userService.forgetPasswordMailSender(email);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
