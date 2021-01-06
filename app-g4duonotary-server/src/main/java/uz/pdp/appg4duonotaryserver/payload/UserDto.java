package uz.pdp.appg4duonotaryserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appg4duonotaryserver.entity.AgentLocation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String phoneNumber;

    @Email
    private String email;

    private boolean active;

    private String password;

    private String prePassword;

    private String oldPassword;

    private String forgetPasswordCode;

    private String changedEmail;

    private boolean onlineAgent;//IN person or online working

    private UUID photoId;//USERNING AVATAR PHOTOSI

    private UUID sharierUserId;

    private List<CertificateDto> certificateDtoList;

    private CertificateDto passportDto;


    private List<Integer> permissionsIds;

    private List<UUID> stateIds;

    private List<UUID> countyIds;

    private List<UUID> zipCodeIds;

    private String onOffTime;

    private Integer orderCount;

    private AgentLocation agentLocation;

    public UserDto(UUID id, @NotNull String firstName, @NotNull String lastName, @NotNull String phoneNumber, @Email String email, String password, String prePassword, String oldPassword, String forgetPasswordCode, String changedEmail, boolean onlineAgent, UUID photoId, UUID sharierUserId, List<CertificateDto> certificateDtoList, CertificateDto passportDto, List<Integer> permissionsIds, List<UUID> stateIds, List<UUID> countyIds, List<UUID> zipCodeIds, String onOffTime, Integer orderCount,AgentLocation agentLocation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.prePassword = prePassword;
        this.oldPassword = oldPassword;
        this.forgetPasswordCode = forgetPasswordCode;
        this.changedEmail = changedEmail;
        this.onlineAgent = onlineAgent;
        this.photoId = photoId;
        this.sharierUserId = sharierUserId;
        this.certificateDtoList = certificateDtoList;
        this.passportDto = passportDto;
        this.permissionsIds = permissionsIds;
        this.stateIds = stateIds;
        this.countyIds = countyIds;
        this.zipCodeIds = zipCodeIds;
        this.onOffTime = onOffTime;
        this.orderCount = orderCount;
        this.agentLocation=agentLocation;

    }

    public UserDto(UUID id, String firstName, String lastName, String phoneNumber, @Email String email, UUID photoId, List<CertificateDto> certificateDtoList, CertificateDto passportDto, List<UUID> stateIds,boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.photoId = photoId;
        this.certificateDtoList = certificateDtoList;
        this.passportDto = passportDto;
        this.stateIds = stateIds;
        this.active=active;
    }

    public UserDto(String firstName, String lastName, String phoneNumber, @Email String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public UserDto(UUID id, String firstName, String lastName, String phoneNumber, @Email String email, UUID photoId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.photoId = photoId;
    }
    public UserDto(UUID id, String firstName, String lastName, String phoneNumber, @Email String email, UUID photoId, String onOffTime, Integer orderCount,AgentLocation agentLocation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.photoId = photoId;
        this.onOffTime=onOffTime;
        this.orderCount=orderCount;
        this.agentLocation=agentLocation;
    }


    public UserDto(UUID id, @NotNull String firstName, @NotNull String lastName, @NotNull String phoneNumber, @Email String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
