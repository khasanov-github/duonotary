package uz.pdp.appg4duonotaryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.entity.UserZipCode;
import uz.pdp.appg4duonotaryserver.entity.ZipCode;
import uz.pdp.appg4duonotaryserver.entity.enums.RoleName;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.UserDto;
import uz.pdp.appg4duonotaryserver.repository.*;
import uz.pdp.appg4duonotaryserver.service.team_2.OrderService;

import java.util.*;

@Service
public class UserService {

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    UserZipCodeRepository userZipCodeRepository;

    @Autowired
    ZipCodeRepository zipCodeRepository;

    @Autowired
    AgentService agentService;

    @Autowired
    OrderService orderService;

    public ApiResponse addAdmin(UserDto userDto) {

        //EMAIL APHONE NUMBERLARNI TEKSHIRISH
        ApiResponse checkEmailAndPhoneNumber = authService.checkEmailAndPhoneNumber(userDto);
        if (!checkEmailAndPhoneNumber.isSuccess())
            return checkEmailAndPhoneNumber;

        UUID emailCode = UUID.randomUUID();

        //DEFAULT PAROL
//        userDto.setPassword(AppConstants.DEFAULT_PASSWORD);

        ApiResponse apiResponse = mailService.sendVerificationToEmail(userDto, String.valueOf(emailCode), false);
        if (!apiResponse.isSuccess()) {
            return apiResponse;
        }

        //ADMIN SIFATIDA USER YASAB OLISH
        User admin = authService.makeUser(userDto, true, emailCode);

        //ADMINGA SUPER ADMIN BELGILAGAN HUQUQLARNI BIRIKTIRISH
        admin.setPermissions(new HashSet<>(permissionRepository.findAllById(userDto.getPermissionsIds())));


        //ADMINNI SAQLAB, BITTA O'ZGARUVCHIGA OLISH
        User saveAdmin = userRepository.save(admin);

        //USER DTO DAN KELGAN ZIP CODE ID ORQALI USERZIPCODE TABLEGA SAQLASH
        userZipCodeRepository.saveAll(Objects.requireNonNull(makeUserZipCodeList(userDto, saveAdmin)));

        //user EMAIL GA XAT YUBORISH
        return new ApiResponse("Congratulation successfully registrated. Admin have to check email address.", true);
    }

    private List<UserZipCode> makeUserZipCodeList(UserDto userDto, User saveAdmin) {
        List<UserZipCode> userZipCodes = new ArrayList<>();
        List<ZipCode> zipCodes;
        if (userDto.getZipCodeIds() != null) {
            zipCodes = zipCodeRepository.findAllById(userDto.getZipCodeIds());
        } else if (userDto.getCountyIds() != null) {
            zipCodes = zipCodeRepository.findAllByCountyIdIn(userDto.getCountyIds());
        } else if (userDto.getStateIds() != null) {
            zipCodes = zipCodeRepository.findAllByCountyStateIdIn(userDto.getStateIds());
        } else
            return null;

        for (ZipCode zipCode : zipCodes) {
            userZipCodes.add(new UserZipCode(
                    saveAdmin,
                    zipCode,
                    true
            ));
        }
        return userZipCodes;
    }


    public ApiResponse editUser(UserDto userDto, User user) {
        try {
            if (userDto.getId().equals(user.getId())){
                ApiResponse checkEmailAndPhoneNumberForEdit = checkEmailAndPhoneNumberForEdit(userDto, user.getId());
                if (!checkEmailAndPhoneNumberForEdit.isSuccess())
                    return checkEmailAndPhoneNumberForEdit;

                boolean emailChanging = !user.getEmail().equals(userDto.getEmail());

                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setPhoneNumber(userDto.getPhoneNumber());
                if (emailChanging) {
                    user.setChangedEmail(userDto.getEmail());
                    UUID emailCode = UUID.randomUUID();
                    user.setEmailCode(emailCode.toString());
                    ApiResponse apiResponse = mailService.sendVerificationToEmail(userDto, String.valueOf(emailCode), true);
                    if (!apiResponse.isSuccess()) {
                        return apiResponse;
                    }
                }
                user.setPhoto(userDto.getPhotoId() == null ? null : attachmentRepository.findById(userDto.getPhotoId()).orElseThrow(() -> new ResourceNotFoundException("getPhoto", "id", userDto.getPhotoId())));
                userRepository.save(user);
                return new ApiResponse("Successfully edited!", true);
            }else {
                ApiResponse checkEmailAndPhoneNumberForEdit = checkEmailAndPhoneNumberForEdit(userDto, userDto.getId());
                if (!checkEmailAndPhoneNumberForEdit.isSuccess())
                    return checkEmailAndPhoneNumberForEdit;
                User editedUser = userRepository.findById(userDto.getId()).orElseThrow(() -> new ResourceNotFoundException("", "", userDto.getId()));
                boolean emailChanging = !editedUser.getEmail().equals(userDto.getEmail());

                editedUser.setFirstName(userDto.getFirstName());
                editedUser.setLastName(userDto.getLastName());
                editedUser.setPhoneNumber(userDto.getPhoneNumber());
                if (emailChanging) {
                    editedUser.setEmail(userDto.getEmail());
                    UUID emailCode = UUID.randomUUID();
                    editedUser.setEmailCode(emailCode.toString());
                    ApiResponse apiResponse = mailService.sendVerificationToEmail(userDto, String.valueOf(emailCode), true);
                    if (!apiResponse.isSuccess()) {
                        return apiResponse;
                    }
                }
                editedUser.setPhoto(userDto.getPhotoId() == null ? null : attachmentRepository.findById(userDto.getPhotoId()).orElseThrow(() -> new ResourceNotFoundException("getPhoto", "id", userDto.getPhotoId())));
                userRepository.save(editedUser);
                return new ApiResponse("Successfully edited!", true);
            }

        } catch (Exception e) {
            return new ApiResponse("Error in edited!", false);
        }
    }

    public ApiResponse checkEmailAndPhoneNumberForEdit(UserDto userDto, UUID userId) {
        if (userRepository.existsByEmailAndIdNot(userDto.getEmail(), userId))
            return new ApiResponse("Email is already exist", false);
        if (userRepository.existsByPhoneNumberAndIdNot(userDto.getPhoneNumber(), userId))
            return new ApiResponse("This phone number is already exist", false);
        return new ApiResponse("", true);
    }

    public ApiResponse changeEnabledUser(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getRoles().stream().noneMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN)))
                return new ApiResponse("You have no access this path", false);
            user.setEnabled(!user.isEnabled());
            userRepository.save(user);
            return new ApiResponse("Successfully " + (user.isEnabled() ? "activate" : "deactivate"), true);
        }
        return new ApiResponse("User not find", false);
    }

    public ApiResponse editAdminPermission(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPermissions(new HashSet<>(permissionRepository.findAllById(userDto.getPermissionsIds())));
            userRepository.save(user);
            return new ApiResponse("Successfully edited", true);
        }
        return new ApiResponse("User not find", false);
    }
@Autowired
AgentLocationRepository agentLocationRepository;

    public UserDto getUser(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPhoto() == null ? null : user.getPhoto().getId(),
                agentService.getAgentOnlineTime(user.getId()),
                orderService.getCountAgentOrders(user.getId()),
                agentLocationRepository.findTopByAgentIdOrderByCreatedAtDesc(user.getId()));
    }

    public ApiResponse editPassword(String changedPassword, String oldPassword, User user) {
        if (passwordEncoder.matches(user.getPassword(), oldPassword)) {
            user.setPassword(passwordEncoder.encode(changedPassword));
            userRepository.save(user);
            return new ApiResponse("Successfully edited", true);
        }
        return new ApiResponse("Invalid old password", false);
    }

    public ApiResponse forgetPasswordMailSender(String email) {
        Optional<User> byEmailOrPhoneNumber = userRepository.findByEmailOrPhoneNumber(email, null);
        if (byEmailOrPhoneNumber.isPresent()){
            User user = byEmailOrPhoneNumber.get();
            UserDto userDto = new UserDto();
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setId(user.getId());
            UUID emailCode = UUID.randomUUID();
            user.setEmailCode(String.valueOf(emailCode));
            mailService.forgetPassword(userDto, String.valueOf(emailCode));
            return new ApiResponse("Successfully new password", true);
        }
        return new ApiResponse("Error", false);
    }
}
