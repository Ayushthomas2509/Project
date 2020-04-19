package ayushproject.ayushecommerce.security;

import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
@Component
public class AuthenticationListener implements ApplicationListener<AbstractAuthenticationEvent> {

    int j=0;
    @Autowired
    UserRepo userRepo;

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent appEvent) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username =  request.getParameter("name");
        if (username==null) {
        }else {
            User user=userRepo.findByname(username);
            j++;
            if (j%3==0){
                if (appEvent.getAuthentication().isAuthenticated()) {
                    user.setFailedAttempts(0);
                    userRepo.save(user);
                    System.out.println("Login successfull");

                } else
                {
                    if(user.getFailedAttempts()>=2)
                    {
                        user.setFailedAttempts(user.getFailedAttempts()+1);
                        user.setEnabled(false);
                    }
                    else {
                        user.setFailedAttempts(user.getFailedAttempts()+1);
                    }
                    userRepo.save(user);
                    System.out.println("Login failed Attempts remaining "+(3-user.getFailedAttempts()));

                }
            }


        }
    }
}
