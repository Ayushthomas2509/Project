package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Cart;
import ayushproject.ayushecommerce.entities.Customer;
import ayushproject.ayushecommerce.entities.Seller;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.exceptions.ConfirmPasswordException;
import ayushproject.ayushecommerce.exceptions.WeakPasswordException;
import ayushproject.ayushecommerce.repo.*;
import ayushproject.ayushecommerce.security.GrantAuthorityImpl;
import ayushproject.ayushecommerce.security.PasswordValidator;
import ayushproject.ayushecommerce.security.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.*;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private EmailService emailService;
    private TokenStore tokenStore;
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Autowired
    PasswordValidator passwordValidator;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    ActivityLogService activityLogService;

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

    public User getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetail = (User) authentication.getPrincipal();
        String username = userDetail.getUsername();
        User user = userRepository.findByname(username);
        return user;
    }


    public Iterable<User> allUsers(Integer offset, Integer size){
        activityLogService.activityLog("All Users are Displayed","user",null);
        return userRepository.allUsers(PageRequest.of(offset,size, Sort.Direction.ASC,"id"));}

    public User findUser(String name){return userRepository.findByname(name);}

    public String addUser(User user){
        activityLogService.activityLog("User is added","user",null);
        userRepository.save(user);
        return "User added";

    }

    public String deleteUser(String name) {
        activityLogService.activityLog("User is deleted","user",null);
        userRepository.delete(userRepository.findByname(name));
        return "User Deleted";
    }

    public String editUser(User user) {
        activityLogService.activityLog("User is edited","user",user.getId());
        userRepository.save(user);
        return "User Updated";
    }

    public User loadUsername(String name){
        User user = userRepository.findByname(name);
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
        //activityLogService.activityLog("Seller is Added","user",null);
        if (userRepository.findByname(user.getName())!=null){
            throw new UsernameNotFoundException("not found"); }
        passwordValidator.setPassword(user.getPassword());
        Set<ConstraintViolation<PasswordValidator>> constraintViolations=validate(passwordValidator);

        if (constraintViolations.size()>0){
            throw new WeakPasswordException();
        }
        else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setAuthoritiesList(Arrays.asList("ROLE_SELLER"));
//            user.setCreatedDate(new Date());
            userRepository.save(user);
            activateAccount(user.getName());
            return messageSource.getMessage("seller.add.message", null, locale);


        }
    }

    public String addCustomer(Customer user,Locale locale){
//        activityLogService.activityLog("Customer is Added","user",null);
        if (userRepository.findByname(user.getName())!=null){
            throw new UsernameNotFoundException("Customer Already Exsists");
        }
        passwordValidator.setPassword(user.getPassword());
        Set<ConstraintViolation<PasswordValidator>> constraintViolations=validate(passwordValidator);
            if (constraintViolations.size()>0){
                throw new WeakPasswordException();
            }
            else if(user.getPassword().equals(user.getConfirmPassword())){
                throw new ConfirmPasswordException("Password and confirmpassword not matched");
            }
            else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setAuthoritiesList(Arrays.asList("ROLE_CUSTOMER"));
//                user.setCreatedDate(new Date());
                User customer = userRepository.save(user);
                Cart cart = new Cart();
                cart.setUserid(customer.getId());
                cartRepository.save(cart);
                activateAccount(user.getName());
                return messageSource.getMessage("customer.add.message",null,locale);
            }


    }


    private Set<ConstraintViolation<PasswordValidator>> validate(PasswordValidator passwordValidator){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator=factory.getValidator();
        return validator.validate(passwordValidator);
    }

     private void sendMail(User user,String subject){
         VerificationToken verificationToken=new VerificationToken(user);
         verificationTokenRepository.save(verificationToken);
         SimpleMailMessage mailMessage=new SimpleMailMessage();
         mailMessage.setTo(user.getEmail());
         mailMessage.setSubject(subject);
         mailMessage.setFrom("ADMIN");
         mailMessage.setText("To Complete The Process " + subject +"Click Here " + "http://localhost:8080/"+subject+
                 "?token="+verificationToken.getVerificationToken());
         emailService.sendEmail(mailMessage);
     }

     public String forgetPassword(String name){
        User presentUser= userRepository.findByname(name);
        if (presentUser!=null){
            sendMail(presentUser,"Password-Reset");
            return "Rest Link Send To Your Mail";
        }
        return "User Dont Exsist";
     }

    public String activateAccount(String name) {
        //activityLogService.activityLog("Account Activation Mail is Send","user",null);
        User newUser = userRepository.findByname(name);
        if (newUser != null) {
            sendMail(newUser,"Account-Activation");
            return "Account Activation link sent to your mail";
        }
        return "Failed";
    }

    public String validateActivationToken(String verificationToken) {
        VerificationToken token= verificationTokenRepository.findByVerificationToken(verificationToken);
        if (token != null) {
            User user = userRepository.findByname(token.getUser().getName());
            user.setFailedAttempts(0);
            user.setEnabled(true);
            user.setNonExpiredPassword(false);
            user.setCreatedDate(LocalDate.now());
            userRepository.save(user);
//            sendMail(user,"Account-Activation");
            return "Account Activated";
        }
        return "Invalid Token";
    }




    public String reEnableUser(String name){
        User newUser = userRepository.findByname(name);
        if(newUser != null){
            sendMail(newUser,"Account Activation");
            return "Account Activation Send ";
        }
        return "Failed";
    }

    public String validateResetToken(String verificationToken,String newPassword,String confirmPassword){
        VerificationToken token= verificationTokenRepository.findByVerificationToken(verificationToken);

        if (token!=null){
            User user = userRepository.findByname(token.getUser().getName());
//            user.setCreatedDate(new Date());
            passwordValidator.setPassword(newPassword);
            Set<ConstraintViolation<PasswordValidator>> constraintViolations=validate(passwordValidator);
            if (constraintViolations.size() > 0){
                throw new WeakPasswordException();
            }
            else {
                if (newPassword.equals(confirmPassword)) {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    return "PASSWORD UPDATED";
                }
                else throw new ConfirmPasswordException("Password Dont Match");
            }
        }
        return "Invalid Token";
    }

    public String enableSeller(String name){
        activityLogService.activityLog("Enabled","user",null);
        User user= userRepository.findByname(name);
        if (user != null){
            user.setEnabled(true);
            userRepository.save(user);
            sendMail(user,"Seller Has Been Activated");
            return "Seller Enabled";
        }
        return "User Not Found";
    }




    public String disableUser(String name) {
        activityLogService.activityLog("Disabled","user",null);
        User user = userRepository.findByname(name);
        if (user != null) {
            user.setEnabled(false);
            userRepository.save(user);
            sendMail(user,"Your account has been Deactivated by admin");
            return "User disabled";
        }
        return "user not found";
    }
}
