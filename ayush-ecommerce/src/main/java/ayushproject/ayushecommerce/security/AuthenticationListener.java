package ayushproject.ayushecommerce.security;

import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.exceptions.PasswordExpiredException;
import ayushproject.ayushecommerce.repo.UserRepository;
import ayushproject.ayushecommerce.services.EmailService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {

    int j = 0;
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    UserService userService;

//    @Override
//    public void onApplicationEvent(AbstractAuthenticationEvent appEvent) {
//
//        HttpServletRequest request;
//        request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
//        String username =  request.getParameter("name");
//        if (username==null) {
//        }else {
//            User user=userRepository.findByname(username);
//            j++;
//            if (j%3==0){
//                if (appEvent.getAuthentication().isAuthenticated()) {
//                    user.setFailedAttempts(0);
//                    userRepository.save(user);
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
//                    userRepository.save(user);
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

        if (appEvent instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;

        }
        if (appEvent instanceof AuthenticationFailureBadCredentialsEvent) {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;

            // add code here to handle unsuccessful login event
            // for example, counting the number of login failure attempts and storing it in db
            // this count can be used to lock or disable any user account as per business requirements
            String username = (String) event.getAuthentication().getPrincipal();
            System.out.println(username);
            User user = userRepository.findByname(username);
            if (user != null) {
                if (user.getFailedAttempts() == 2) {
                    user.setIs_active(false);
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(user.getEmail());
                    mailMessage.setSubject("Account Security Issue");
                    mailMessage.setFrom("ADMIN");
                    mailMessage.setText("Your Account has been temporally blocked ");
                    emailService.sendEmail(mailMessage);
                    SimpleMailMessage mailMessage1 = new SimpleMailMessage();
                    mailMessage1.setTo("ayush.thomas@tothenew.com");
                    mailMessage1.setSubject("Regarding Activation");
                    mailMessage1.setFrom("ADMIN");
                    mailMessage1.setText("Hi Admin bhaiya " + username + " is locked.");
                    emailService.sendEmail(mailMessage1);

                } else {
                    user.setFailedAttempts(user.getFailedAttempts() + 1);
                }
                userRepository.save(user);}}}}

//        } else {
//
//        }
//        AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
//        String username = event.getAuthentication().getPrincipal().toString();
//        User user = userRepository.findByname(username);
//       // System.out.println(username + "\n\n\n\n\n  \n\n\n\n" + user.getFirstName());
//        if (user.setNonExpiredPassword(true)) {
//            throw new PasswordExpiredException("your password is expired.");
//        }
//        }
//        if (appEvent instanceof AuthenticationSuccessEvent) {
//            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;
//            String username = event.getAuthentication().getPrincipal().toString();
//            if(!username.equals(null))
//                System.out.println("mai chal ra hu");



//        }

//        @EventListener
//        public void AuthenticationPassword(AuthenticationSuccessEvent event){
////            String username = (String) event.getAuthentication().getPrincipal();
////            User user = userRepository.findByname(username);
////            System.out.println("hello jii "+username);
////            System.out.println(user.getEmail());
////            if (user.isExpiredPassword()) {
////                throw new PasswordExpiredException("your password is expired.");
////            }
//        }
//    }
