package uz.pdp.appg4duonotaryserver.service;


import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.appg4duonotaryserver.entity.*;
import uz.pdp.appg4duonotaryserver.entity.enums.RoleName;
import uz.pdp.appg4duonotaryserver.entity.enums.StatusEnum;
import uz.pdp.appg4duonotaryserver.exceptions.ResourceNotFoundException;
import uz.pdp.appg4duonotaryserver.payload.ApiResponse;
import uz.pdp.appg4duonotaryserver.payload.CertificateDto;
import uz.pdp.appg4duonotaryserver.payload.PassportDto;
import uz.pdp.appg4duonotaryserver.payload.UserDto;
import uz.pdp.appg4duonotaryserver.repository.*;
import uz.pdp.appg4duonotaryserver.utils.CommonUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AgentService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CertificateRepository certificateRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    CountyRepository countyRepository;
    @Autowired
    PassportRepository passportRepository;
    @Autowired
    ZipCodeRepository zipCodeRepository;
    @Autowired
    UserZipCodeRepository userZipCodeRepository;
    @Autowired
    MailService mailService;
    @Autowired
    StateRepository stateRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;
    @Autowired
    AgentOnlineOfflineRepository agentOnlineOfflineRepository;




    public ApiResponse addCertificates(List<CertificateDto> certificateDtos, User user) {
        try {
            certificateRepository.saveAll(certificateDtos.stream().map(certificateDto -> makeCertificate(certificateDto, user, new Certificate())).collect(Collectors.toList()));
            return new ApiResponse("saved all Certificates", true);
        } catch (Exception e) {
            return new ApiResponse("an error occurred during storage", false);
        }
    }

    /**
     * Passport ni saqlash uchun method
     *
     * @param user user
     * @return ApiResponse object=null
     */
    public ApiResponse addPassport(CertificateDto passportDto, User user) {
        try {
            passportRepository.save(makePassport(passportDto, user, new Passport()));
            return new ApiResponse("saved", true);
        } catch (Exception e) {
            return new ApiResponse("an error occurred during storage", false);
        }
    }

    /**
     * CertificateDtodan Certificatega o'tkazadigan method
     *
     * @param certificateDto dto
     * @param user           user
     * @return Certificate
     */
    public Certificate makeCertificate(CertificateDto certificateDto, User user, Certificate certificate) {
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_AGENT))) {
            certificate.setUser(user);
            certificate.setStatusEnum(StatusEnum.PENDING);
        }
        certificate.setAttachment(attachmentRepository.findById(certificateDto.getAttachmentId()).orElseThrow(() -> new ResourceNotFoundException("getCertificate", "id", certificateDto.getAttachmentId())));
        certificate.setIssueDate(certificateDto.getIssueDate());
        certificate.setExpireDate(certificateDto.getExpireDate());
        certificate.setState(stateRepository.findById(certificateDto.getStateId()).orElseThrow(() -> new ResourceNotFoundException("getState", "id", certificateDto.getStateId())));
        certificate.setExpired(false);
        return certificate;
    }


    /**
     * CertificateDtodan Passportga o'tkazadigan method
     *
     * @param user user
     * @return Passport
     */
    public Passport makePassport(CertificateDto passportDto, User user, Passport passport) {
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_AGENT)) ||
                user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_ADMIN))) {
            passport.setUser(user);
            passport.setStatusEnum(StatusEnum.PENDING);
        }
        passport.setAttachment(attachmentRepository.findById(passportDto.getAttachmentId()).orElseThrow(() -> new ResourceNotFoundException("getCertificate", "id", passportDto.getAttachmentId())));
        passport.setIssueDate(passportDto.getIssueDate());
        passport.setExpireDate(passportDto.getExpireDate());
        passport.setExpired(false);
        return passport;
    }

    /**
     * Agentning sertificatedagi county_id bo`yicha zipCode tabledan mos zipCodelarni listini olib beradi;
     *
     * @return List<zipCode
     */
    public List<ZipCode> getZipCodes(UUID state_id) {
        return zipCodeRepository.findAllByCountyStateId(state_id);
    }

    public ApiResponse getAgentsForAdmin(int page, int size) {
        try {
            Set<Role> allByName = roleRepository.findAllByName(RoleName.ROLE_AGENT);
            Page<User> all = userRepository.findAllByRolesIn(allByName, CommonUtils.getPageable(page, size));
            return new ApiResponse("success", true, all.getContent().stream().map(user -> userService.getUser(user)).collect(Collectors.toList()), page, all.getTotalElements());
        } catch (IllegalArgumentException e) {
            return new ApiResponse("Error", false);
        }
    }

    public String getAgentOnlineTime(UUID agentId) {
        List<Object[]> objects = agentOnlineOfflineRepository.lastTwo(agentId);
        long duration = 0;
        for (Object[] object : objects) {
            Timestamp timestamp0 = (Timestamp) object[0];
            for (Object[] object1 : objects) {
                Timestamp timestamp1 = (Timestamp) object1[0];
                if (!timestamp0.equals(timestamp1)) {
                    LocalDateTime time0 = timestamp0.toLocalDateTime();
                    LocalDateTime time1 = timestamp1.toLocalDateTime();
                    duration = Duration.between(time1, time0).toMinutes();
                    String hm = String.format("%02d:%02d", TimeUnit.MINUTES.toHours(duration),
                            TimeUnit.MINUTES.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours(duration)));
                    return hm;
                }
            }
        }
        return "Error";
    }


    public ApiResponse getSearched(int page, int size, String search) {
        try {
            Set<Role> roleAgent = roleRepository.findAllByName(RoleName.ROLE_AGENT);
            Pageable pageable = CommonUtils.getPageable(page, size);
            List<User> all = userRepository.searchAgents(search);
            List<User> byRole=new ArrayList<>();
            for (User user : all) {
                User agent = userRepository.findByIdAndRolesIn(user.getId(), roleAgent);
                if (agent!=null)
                byRole.add(agent);
            }
            Page<User> pageUsers = new PageImpl<>(byRole, pageable, byRole.size());
            return new ApiResponse("success", true, pageUsers.getContent().stream().map(user -> userService.getUser(user)).collect(Collectors.toList()), page, pageUsers.getTotalElements());
        } catch (IllegalArgumentException e) {
            return new ApiResponse("Error", false);
        }
    }


    /**
     * Certificate ni CertificateDto ga o'rgiruvchi method
     *
     * @param certificate certificate
     * @return CertirficateDto dto
     */
    public CertificateDto getCertificate(Certificate certificate) {
        return new CertificateDto(
                certificate.getId(),
                certificate.getAttachment().getId(),
                userService.getUser(certificate.getUser()),
                certificate.getState() == null ? null : certificate.getState().getId(),
                certificate.getIssueDate(),
                certificate.getExpireDate(),
                certificate.getStatusEnum(),
                certificate.isExpired()

        );
    }


    /**
     * Passport ni CertificateDto ga o'rgiruvchi method
     *
     * @param passport passport
     * @return CertirficateDto CertirficateDto
     */
    public CertificateDto getPassport(Passport passport) {
        return new CertificateDto(
                passport.getId(),
                passport.getAttachment().getId(),
                userService.getUser(passport.getUser()),
                null,
                passport.getIssueDate(),
                passport.getExpireDate(),
                passport.getStatusEnum(),
                passport.isExpired()
        );
    }

    public List<CertificateDto> getCertificatesByUserId(UUID userId) {
        return certificateRepository.findAllByUser_Id(userId).stream().map(this::getCertificate).collect(Collectors.toList());
    }

    public CertificateDto getPassportsByUser(UUID userId) {
        Optional<Passport> byUser_id = passportRepository.findByUser_Id(userId);
        return byUser_id.map(this::getPassport).orElse(null);

    }

    public UserDto getAgent(User user) {
        List<CertificateDto> certificatesByUserId = getCertificatesByUserId(user.getId());
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPhoto().getId(),
                certificatesByUserId,
                getPassportsByUser(user.getId()),
                certificatesByUserId.stream().map(CertificateDto::getStateId).collect(Collectors.toList()),
                user.isActive()
        );
    }

    public ApiResponse activateOrDeactivate(UUID agentId, List<UUID> zipCodesId) {
        Optional<User> optionalUser = userRepository.findById(agentId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            boolean agent = user.getRoles().stream().anyMatch(role -> role.getName().equals(RoleName.ROLE_AGENT));
            if (!agent)
                return new ApiResponse("This user not agent", false);
            user.setActive(!user.isActive());
            userRepository.save(user);
            if (user.isActive()) {
                new Thread(() -> setUserZipCode(user, zipCodesId)).start();
            }
            return new ApiResponse("success", true);
        }
        return new ApiResponse("User not found", false);
    }

    public void setUserZipCode(User agent, List<UUID> zipCodesId) {//15 ta keldi zipcodesId
        List<UserZipCode> userZipCodes = userZipCodeRepository.findAllByUser_id(agent.getId());//20 ta bor edi zipcodesId
        for (UserZipCode userZipCode : userZipCodes) {
            if (!zipCodesId.contains(userZipCode.getZipCode().getId()))
                userZipCode.setActive(false);
            else {
                zipCodesId.remove(userZipCode.getZipCode().getId());
                userZipCode.setActive(true);
            }
        }
        userZipCodeRepository.saveAll(userZipCodes);
        userZipCodeRepository.saveAll(zipCodesId.stream().map(uuid -> new UserZipCode(agent,
                zipCodeRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("getZipCodes", "id", userZipCodes)),
                 true)).collect(Collectors.toList()));
    }

    public ApiResponse editStatus(UUID documentId, StatusEnum statusEnum,String description) throws MessagingException, IOException, TemplateException {

        if (statusEnum.equals(StatusEnum.REJECTED) && description.length()<5)
            return new ApiResponse("description is Null",false);
        Optional<Passport> passportOptional = passportRepository.findById(documentId);
        if (!passportOptional.isPresent()) {
            Optional<Certificate> certificateOptional = certificateRepository.findById(documentId);
            if (certificateOptional.isPresent()) {
                certificateOptional.get().setStatusEnum(statusEnum);
                new Thread(() -> {
                    try {
                        mailService.agentInfoChange(getCertificate( certificateRepository.save(certificateOptional.get())),description);
                    } catch (Exception e) {
                        e.printStackTrace();}

                }).start();
                return new ApiResponse("success", true);
            }
            return new ApiResponse("none", false);
        }
        passportOptional.get().setStatusEnum(statusEnum);

        new Thread(() -> {
            try {
                mailService.agentInfoChange(getPassport(passportRepository.save(passportOptional.get())),description);
            } catch (Exception e) {
                e.printStackTrace();}
        }).start();
        return new ApiResponse("success", true);
    }

}
