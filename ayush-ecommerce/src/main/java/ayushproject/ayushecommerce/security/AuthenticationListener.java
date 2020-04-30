package ayushproject.ayushecommerce.security;

import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.repo.UserRepo;
import ayushproject.ayushecommerce.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {

    int j=0;
    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailService emailService;

//    @Override
//    public void onApplicationEvent(AbstractAuthenticationEvent appEvent) {
//
//        HttpServletRequest request;
//        request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
//        String username =  request.getParameter("name");
//        if (username==null) {
//        }else {
//            User user=userRepo.findByname(username);
//            j++;
//            if (j%3==0){
//                if (appEvent.getAuthentication().isAuthenticated()) {
//                    user.setFailedAttempts(0);
//                    userRepo.save(user);
//                    System.out.println("Login successful");
//
//                } else
//                {
//                    if(user.getFailedAttempts()>=2)
//                    {
//                        user.setFailedAttempts(user.getFailedAttempts()+1);
//                        user.setEnabled(false);
//                    }
//                    else {
//                        user.setFailedAttempts(user.getFailedAttempts()+1);
//                    }
//                    userRepo.save(user);
//                    System.out.println("Login failed Attempts remaining "+(3-user.getFailedAttempts()));
//
//                }
//            }
//
//
//        }
//    }

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent appEvent) {

        if (appEvent instanceof AuthenticationSuccessEvent)
        {
            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
            // add code here to handle successful login event
        }

        if (appEvent instanceof AuthenticationFailureBadCredentialsEvent) {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;

            // add code here to handle unsuccessful login event
            // for example, counting the number of login failure attempts and storing it in db
            // this count can be used to lock or disable any user account as per business requirements
            String username = (String) event.getAuthentication().getPrincipal();
            User user = userRepo.findByname(username);
            if (user != null) {
                if (user.getFailedAttempts() == 2) {
                    user.setIs_active(false);
                    SimpleMailMessage mailMessage=new SimpleMailMessage();
                    mailMessage.setTo(user.getEmail());
                    mailMessage.setSubject("Account Security Issue");
                    mailMessage.setFrom("ADMIN");
                    mailMessage.setText("Your Account has been temporally blocked ");
                    emailService.sendEmail(mailMessage);

                } else {
                    user.setFailedAttempts(user.getFailedAttempts() + 1);
                }
                userRepo.save(user);
            }
        }
    }
}
