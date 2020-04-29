package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Customer;
import ayushproject.ayushecommerce.entities.Seller;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.exceptions.WeakPasswordEx;
import ayushproject.ayushecommerce.repo.*;
import ayushproject.ayushecommerce.security.GrantAuthorityImpl;
import ayushproject.ayushecommerce.security.PasswordValidatorClass;
import ayushproject.ayushecommerce.security.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Component
public class UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    SellerRepo sellerRepo;
    @Autowired
    VerificationTokenRepo verificationTokenRepo;
    @Autowired
    private EmailService emailService;
    private TokenStore tokenStore;
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Autowired
    PasswordValidatorClass passwordValidatorClass;
    @Autowired
    private MessageSource messageSource;;

    @PreAuthorize("hasRole('ADMIN')")
    public boolean ensureAdmin(){return true;
    }

    @PreAuthorize("hasRole('CUSTOMER')||hasRole('SELLER')||hasRole('ADMIN')")
    public boolean ensureUser(){return true;}

    @PreAuthorize("hasRole('CUSTOMER')")
    public void ensureCustomer(){
    }

    @PreAuthorize("hasRole('SELLER')")
    public void ensureSeller(){
    }

    @PreAuthorize("hasRole('CUSTOMER')||hasRole('ADMIN')")
    public boolean ensureCustomerOrAdmin(){return true;}

    public Iterable<User> allUsers(){return userRepo.findAll();}

    public User findUser(String name){return userRepo.findByname(name);}

    public String addUser(User user){
        userRepo.save(user);
        return "User added";

    }

    public String deleteUser(String name) {
        userRepo.delete(userRepo.findByname(name));
        return "User Deleted";
    }

    public String editUser(User user) {
        userRepo.save(user);
        return "User Updated";
    }

    public User loadUsername(String name){
        User user = userRepo.findByname(name);
        System.out.println(user);
        if (name != null) {
            List<GrantAuthorityImpl> grantAuthoritiesList = new ArrayList<>();
            for (int i=0;i<user.getAuthoritiesList().size();i++) {
                grantAuthoritiesList.add(new GrantAuthorityImpl(user.getAuthoritiesList().get(i)));
            }
            user.setGrantAuthorities(grantAuthoritiesList);
            return user;
        } else {
            throw new UsernameNotFoundException("Invalid username "+ name);
        }
    }

    public String addSeller(Seller user, Locale locale){
        if (userRepo.findByname(user.getName())!=null){
            throw new UsernameNotFoundException("not found"); }
        passwordValidatorClass.setPassword(user.getPassword());
        Set<ConstraintViolation<PasswordValidatorClass>> constraintViolations=validate(passwordValidatorClass);

        if (constraintViolations.size()>0){
            throw new WeakPasswordEx();
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
           // activateAccount(user.getName());
            return messageSource.getMessage("seller.add.message", null, locale);


        }
    }

    public String addCustomer(Customer user,Locale locale){
        if (userRepo.findByname(user.getName())!=null){
            throw new UsernameNotFoundException("Customer Already Exsists");
        }
        passwordValidatorClass.setPassword(user.getPassword());
        Set<ConstraintViolation<PasswordValidatorClass>> constraintViolations=validate(passwordValidatorClass);
            if (constraintViolations.size()>0){
                throw new WeakPasswordEx();
            }
            else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepo.save(user);
                activateAccount(user.getName());
                return messageSource.getMessage("customer.add.message",null,locale);
            }


    }


    private Set<ConstraintViolation<PasswordValidatorClass>> validate(PasswordValidatorClass passwordValidatorClass){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator=factory.getValidator();
        return validator.validate(passwordValidatorClass);
    }

     private void sendMail(User user,String subject){
         VerificationToken verificationToken=new VerificationToken(user);
         verificationTokenRepo.save(verificationToken);
         SimpleMailMessage mailMessage=new SimpleMailMessage();
         mailMessage.setTo(user.getEmail());
         mailMessage.setSubject(subject);
         mailMessage.setFrom("ADMIN");
         mailMessage.setText("To Complete The Process " + subject +"Click Here " + "http://localhost:8080/"+subject+
                 "?token="+verificationToken.getVerificationToken());
         emailService.sendEmail(mailMessage);
     }

     public String forgetPassword(String name){
        User presentUser=userRepo.findByname(name);
        if (presentUser!=null){
            sendMail(presentUser,"Password-Reset");
            return "Rest Link Send To Your Mail";
        }
        return "User Dont Exsist";
     }

    public String activateAccount(String name) {
        User newUser = userRepo.findByname(name);
        if (newUser != null) {
            sendMail(newUser,"Account-Activation");
            return "Account Activation link sent to your mail";
        }
        return "Failed";
    }
    public String validateActivationToken(String verificationToken) {
        VerificationToken token=verificationTokenRepo.findByVerificationToken(verificationToken);
        if (token != null) {
            User user = userRepo.findByname(token.getUser().getName());
            user.setFailedAttempts(0);
            user.setEnabled(true);
            userRepo.save(user);
            sendMail(user,"Account-Activation");
            return "Account Activated";
        }
        return "Invalid Token";
    }




    public String reEnableUser(String name){
        User newUser = userRepo.findByname(name);
        if(newUser != null){
            sendMail(newUser,"Account Activation");
            return "Account Activation Send ";
        }
        return "Failed";
    }

    public String validateResetToken(String verificationToken,String newPassword,String confirmPassword){
        VerificationToken token=verificationTokenRepo.findByVerificationToken(verificationToken);

        if (token!=null){
            User user = userRepo.findByname(token.getUser().getName());
            passwordValidatorClass.setPassword(newPassword);
            Set<ConstraintViolation<PasswordValidatorClass>> constraintViolations=validate(passwordValidatorClass);
            if (constraintViolations.size() > 0){
                throw new WeakPasswordEx();
            }
            else {
                if (newPassword.equals(confirmPassword)) {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepo.save(user);
                    return "PASSWORD UPDATED";
                }
                else {
                    return "ConfirmPassword Dont Match";

                }
            }
        }
        return "Invalid Token";
    }

    public String enableSeller(String name){
        User user= userRepo.findByname(name);
        if (user != null){
            user.setEnabled(true);
            userRepo.save(user);
            sendMail(user,"Seller Has Been Activated");
            return "Seller Enabled";
        }
        return "User Not Found";
    }




    public String disableUser(String name) {
        User user = userRepo.findByname(name);
        if (user != null) {
            user.setEnabled(false);
            userRepo.save(user);
            sendMail(user,"Your account has been Deactivated by admin");
            return "User disabled";
        }
        return "user not found";
    }
}
