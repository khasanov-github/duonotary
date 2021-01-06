package uz.pdp.appg4duonotaryserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.appg4duonotaryserver.entity.template.AbsEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {

    @Column(nullable = false)
    private String firstName;

   @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String emailCode;

    private String changedEmail;

    private boolean active;//AGENT UCHUN. AGAR FALSE BO'LSA ZAKAZ OLOLMAYDI VA ZAKAZ BILAN ISHLOLMAYDI

    private boolean online;//AGENT UCHUN. APPLICATION DAGI HOLATI

    private boolean onlineAgent;//IN person or online working

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;//USERNING ROLELARI

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_permission",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Permission> permissions;//USERNING HUQUQLARI

    @OneToOne
    private Attachment photo;//USERNING AVATAR PHOTOSI

    @ManyToOne(fetch = FetchType.LAZY)
    private User sharier;


    private String stripeCustomerId;

    private boolean isAccountNonExpired = true;

    private boolean isAccountNonLocked = true;

    private boolean isCredentialsNonExpired = true;

    private boolean enabled = false;

    public User(String firstName, String lastName, String phoneNumber, String email, String password, Set<Role> roles, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.enabled = enabled;
    }



    public User(String firstName, String lastName, String phoneNumber, String email,
                String password, String emailCode, Set<Role> roles, User sharier, boolean enabled, Set<Permission> permissions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.emailCode = emailCode;
        this.roles = roles;
        this.sharier = sharier;
        this.enabled = enabled;
        this.permissions = permissions;
    }

    public User(String firstName, String lastName, String phoneNumber, String email,
                String password, String emailCode, Set<Role> roles, User sharier, boolean enabled, Attachment photo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.emailCode = emailCode;
        this.roles = roles;
        this.sharier = sharier;
        this.enabled = enabled;
        this.photo = photo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthoritySet=new HashSet<>();
        if(permissions!=null)
        grantedAuthoritySet.addAll(permissions);
        grantedAuthoritySet.addAll(roles);

        return grantedAuthoritySet;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
