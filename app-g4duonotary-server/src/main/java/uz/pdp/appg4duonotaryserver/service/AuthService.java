package uz.pdp.appg4duonotaryserver.service;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.User;
import uz.pdp.appg4duonotaryserver.entity.enums.RoleName;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.CertificateDto;
import uz.pdp.appg4duonotaryserver.payload.UserDto;
import uz.pdp.appg4duonotaryserver.repository.AttachmentRepository;
import uz.pdp.appg4duonotaryserver.repository.RoleRepository;
import uz.pdp.appg4duonotaryserver.repository.UserRepository;
import uz.pdp.appg4duonotaryserver.repository.UserZipCodeRepository;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    MailService mailService;

    @Autowired
    UserZipCodeRepository userZipCodeRepository;

    @Autowired
    AgentService agentService;

    public UserDetails getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("getUser"));
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        return userRepository.findByEmailOrPhoneNumber(userName, userName).orElseThrow(() -> new UsernameNotFoundException("get user name"));
    }

    public String trimPhoneNumber(String username) {
        return "+" + username.substring(1);
    }


    public ApiResponse registerUser(UserDto userDto) {
        try {
            ApiResponse checkPassword = checkPassword(userDto);
            if (!checkPassword.isSuccess())
                return checkPassword;
            ApiResponse checkEmailAndPhoneNumber = checkEmailAndPhoneNumber(userDto);
            if (!checkEmailAndPhoneNumber.isSuccess())
                return checkEmailAndPhoneNumber;
            UUID emailCode = UUID.randomUUID();
            userRepository.save(makeUser(userDto, false, emailCode));
            mailService.sendVerificationToEmail(userDto, String.valueOf(emailCode),  false);
            return new ApiResponse("Congratulation you are successfully registrated. please check your email address.", true);
        } catch (Exception e) {
            return new ApiResponse("Failed to save user", false);
        }
    }

    public User makeUser(UserDto userDto, Boolean admin, UUID emailCode) {
        return new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getPhoneNumber(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                String.valueOf(emailCode),
                roleRepository.findAllByName(admin == null ? RoleName.ROLE_AGENT :
                        admin ? RoleName.ROLE_ADMIN
                                : RoleName.ROLE_USER
                ),
                userDto.getSharierUserId() == null ? null : userRepository.findById(userDto.getSharierUserId()).orElse(null),
                admin != null && admin,
                userDto.getPhotoId()==null?null:attachmentRepository.findById(userDto.getPhotoId()).orElse(null)
        );
    }

    /**
     * password va phone numberni tekshirish
     *
     * @param userDto
     * @return
     */
    public ApiResponse checkEmailAndPhoneNumber(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail()))
            return new ApiResponse("Email is already exist", false);
        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber()))
            return new ApiResponse("This phone number is already exist", false);
        return new ApiResponse("", true);
    }

    /**
     * PAROLNI TEKSHIRADIGAN METHOD
     *
     * @param userDto
     * @return
     */
    public ApiResponse checkPassword(UserDto userDto) {
        if (userDto.getPassword().length() < 6 || userDto.getPassword().length() > 16)
            return new ApiResponse("Password size between 6 and 16 character!", false);
        if (!(userDto.getPassword().equals(userDto.getPrePassword())))
            return new ApiResponse("Password and prepassword is not equals!", false);
        return new ApiResponse("", true);
    }

    /**
     * BU METHOD AGENT RO'YXATDAN O'TADI
     */
    public ApiResponse registerAgent(UserDto userDto) throws MessagingException, IOException, TemplateException {
        ApiResponse checkPassword = checkPassword(userDto);
        if (!checkPassword.isSuccess())
            return checkPassword;
        ApiResponse checkEmailAndPhoneNumber = checkEmailAndPhoneNumber(userDto);//userning passwordlarini mosligini, email va phoneNumberning o'xshashi yo'qligini
        if (!checkEmailAndPhoneNumber.isSuccess())
            return checkEmailAndPhoneNumber;
        ApiResponse notValidPassportAndCertificate = notValidPassportAndCertificate(userDto);//ID KARTA VA CERTIFICATE LARNI XATOLIGINI TEKSHIRIB BERADI
        if (!notValidPassportAndCertificate.isSuccess())
            return notValidPassportAndCertificate;
        UUID emailCode = UUID.randomUUID();
        ApiResponse apiResponse = mailService.sendVerificationToEmail(userDto, String.valueOf(emailCode), false);
        if (!apiResponse.isSuccess()){
            return apiResponse;
        }
        User user = makeUser(userDto, null, emailCode);
        User savedUser = userRepository.save(user);

        agentService.addPassport(userDto.getPassportDto(), savedUser);
        agentService.addCertificates(userDto.getCertificateDtoList(), savedUser);

        return new ApiResponse("Successfully registered. Verify your email. After wait activate admin", true);
    }

    /**
     * id card va certificate larni ishonchli emasligini tekshirish uchun
     *
     * @param userDto
     * @return
     */
    public ApiResponse notValidPassportAndCertificate(UserDto userDto) {
        ApiResponse passportExpired = CommonUtils.agentCertificateOrPassportExpired(userDto.getPassportDto());
        if (passportExpired.isSuccess())
            return passportExpired;
        ApiResponse passportNotValidDate = CommonUtils.agentCertificateOrPassportNotValidDate(userDto.getPassportDto());
        if (passportNotValidDate.isSuccess())
            return passportNotValidDate;
        if (userDto.getCertificateDtoList().size() > 0) {
            for (CertificateDto certificateDto : userDto.getCertificateDtoList()) {
                ApiResponse certificateExpired = CommonUtils.agentCertificateOrPassportExpired(certificateDto);
                if (certificateExpired.isSuccess())
                    return certificateExpired;
                ApiResponse certificateNotValidDate = CommonUtils.agentCertificateOrPassportNotValidDate(certificateDto);
                if (certificateNotValidDate.isSuccess())
                    return certificateNotValidDate;
            }
        }
        return new ApiResponse("Ok", true);
    }

    public ApiResponse verifyEmail(String emailCode, boolean isAccept, boolean changeEmail) {
        Optional<User> byEmailCode = userRepository.findByEmailCode(emailCode);
        if (byEmailCode.isPresent()) {
            User user = byEmailCode.get();
            user.setEnabled(isAccept);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse(isAccept ? "successfully verified!" : "Verification canceled.", true);
        } else {
            return new ApiResponse("ERROR: 403! User not found", true);
        }
    }
}