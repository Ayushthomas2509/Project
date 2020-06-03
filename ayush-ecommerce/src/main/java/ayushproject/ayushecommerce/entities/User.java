package ayushproject.ayushecommerce.entities;

import ayushproject.ayushecommerce.security.GrantAuthorityImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type",discriminatorType = DiscriminatorType.STRING)
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails , Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;
    @NotNull(message = "name cannot be empty")
   @Audited
    private String name;
//    @Audited
    private Date dob;
    @Transient
    @JsonIgnore
    private String confirmPassword;
    @Audited
    @NotNull(message = "First name cannot be null")
    protected String firstName;
    @Email
    @Audited
    @Column(unique = true)
    private String email;
    @Audited
    private String lastName;
    @Audited
    private String gender;
    @Audited
    private Integer age;
//    @Audited
    @NotNull(message = "Password Cannot Be Null")
    private String password;
   @Audited
    private boolean enabled=false;
    private boolean deleted=false;
    private Integer failedAttempts=0;
    private boolean nonExpiredPassword=true;

//    @CreatedDate
    private LocalDate CreatedDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDate modifiedDate;
    @JsonIgnore
//    @Audited
    @ElementCollection(fetch = FetchType.EAGER)
    List<String> authoritiesList;
    @ElementCollection
    List<GrantAuthorityImpl> grantAuthorities;
    public User(String username, String password, List<GrantAuthorityImpl> grantAuthorities) {
        this.name = name;
        this.password = password;
        this.grantAuthorities = grantAuthorities;     }

    public User(String name, Date dob, String firstName, String email, String lastName, Integer age, String password, List<String> authoritiesList) {
        this.name = name;
        this.dob = dob;
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.authoritiesList = authoritiesList;
    }

    public User() {
    }


    public Integer getId() {
        return id;
    }

    public List<GrantAuthorityImpl> getGrantAuthorities() {
        return grantAuthorities;
    }

    public void setGrantAuthorities(List<GrantAuthorityImpl> grantAuthorities) {
        this.grantAuthorities = grantAuthorities;
    }

    public List<String> getAuthoritiesList() {
        return authoritiesList;
    }

    public void setAuthoritiesList(List<String> authoritiesList) {
        this.authoritiesList = authoritiesList;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Integer failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    private Boolean is_active = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return nonExpiredPassword;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return is_active;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

//    public Date getCreatedDate() {
//        return CreatedDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        CreatedDate = createdDate;
//    }

    public boolean isNonExpiredPassword() {
        return nonExpiredPassword;
    }

    public boolean setNonExpiredPassword(boolean nonExpiredPassword) {
        this.nonExpiredPassword = nonExpiredPassword;
        return nonExpiredPassword;
    }

    public LocalDate getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        CreatedDate = createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
