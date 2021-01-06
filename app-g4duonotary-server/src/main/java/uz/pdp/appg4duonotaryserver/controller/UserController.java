package uz.pdp.appg4duonotaryserver.controller;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.UserDto;
import uz.pdp.appg4duonotaryserver.repository.UserRepository;
import uz.pdp.appg4duonotaryserver.security.CurrentUser;
import uz.pdp.appg4duonotaryserver.service.UserService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    
    @PostMapping("/addAdmin")
    public HttpEntity<?> addAdmin(@RequestBody UserDto userDto) {
        ApiResponse response = userService.addAdmin(userDto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }


    @PostMapping("/changeEnabledAdmin/{adminId}")
    public HttpEntity<?> changeEnabledAdmin(@PathVariable UUID adminId) {
        ApiResponse response = userService.changeEnabledUser(adminId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PostMapping("/editAdminPermission")
    public HttpEntity<?> editAdminPermission(@RequestBody UserDto userDto) {
        ApiResponse response = userService.editAdminPermission(userDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @PutMapping("/edit")
    public HttpEntity<?> editUser(@RequestBody UserDto userDto, @CurrentUser User user) throws MessagingException, IOException, TemplateException {
        ApiResponse response = userService.editUser(userDto, user);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/me")
    public HttpEntity<?> getUserMe(@CurrentUser User user) {
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public HttpEntity<?> getUserById(@PathVariable UUID userId) {
        User user = userRepository.findById(userId).orElseGet(null);
        return ResponseEntity.ok(user==null? "User not find":userService.getUser(user));
    }

}
