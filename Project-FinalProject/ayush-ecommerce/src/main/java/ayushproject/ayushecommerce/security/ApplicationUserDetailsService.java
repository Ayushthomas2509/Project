package ayushproject.ayushecommerce.security;

import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        String encryptedPassword = passwordEncoder.encode("pass");
        System.out.println("Trying To Authenticate User:::" + name);
        System.out.println("Encrypted Password ::" + encryptedPassword);
        UserDetails userDetails=userService.loadUsername(name);
        return userDetails;
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;
}
