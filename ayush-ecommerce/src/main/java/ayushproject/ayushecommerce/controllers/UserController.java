package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Customer;
import ayushproject.ayushecommerce.entities.Seller;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.services.Db2CsvExporterService;
import ayushproject.ayushecommerce.services.LogService;
import ayushproject.ayushecommerce.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.relational.core.sql.In;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Locale;

@RestController
public class UserController implements Serializable {
    @Autowired
    UserService userService;
    @Autowired
    TokenStore tokenStore;
    @Autowired
    LogService logService;
    @Autowired
    Db2CsvExporterService db2CsvExporterService;

    private static final Logger logger=LoggerFactory.getLogger(UserController.class);

    @Cacheable(value = "user")
    @GetMapping("/users/{offset}/{size}")
    public Iterable<User> allUsers(@PathVariable Integer offset,@PathVariable Integer size){
        logger.info(logService.info("Method Accessed"));
        userService.ensureAdmin();
        return userService.allUsers(offset,size);
    }

    @Cacheable(value = "user")
    @GetMapping("users/{name}")
    public User findUser(@PathVariable String name)
    {     userService.ensureUser();
         return userService.findUser(name);
    }

//    @Cacheable(value = "user", key = "#Id")
    @PutMapping("users/edit")
    public String editUser(@RequestBody User user){
        userService.ensureUser();
        return userService.editUser(user);
    }
    @CacheEvict(value = "user", allEntries=true)
    @DeleteMapping("/users/remove/{name}")
    public String deleteUser(@PathVariable String name)
    {   userService.ensureAdmin();
        return userService.deleteUser(name);
    }
    @CachePut(value = "user", key = "#name")
    @PostMapping("/forgot-password/{name}")
    public String forgotUserPassword(@PathVariable String name){
        return userService.forgetPassword(name);
    }
    @Cacheable(value = "user", key = "#Id")
    @GetMapping("/Password-Reset")
    public String validateResetToken(@RequestParam("token") String verificationToken,@RequestParam("newPassword") String newPassword,@RequestParam("confirmPassword") String confirmPassword) {
        return userService.validateResetToken(verificationToken,newPassword,confirmPassword);
    }

    @GetMapping("/Account-Activation")
    public String validateActivationToken(@RequestParam("token") String verificationToken) {
        return userService.validateActivationToken(verificationToken);
    }

    @Cacheable(value = "user", key = "#userId")
    @GetMapping("/Enable-seller/{name}")
    public String enableSeller(@PathVariable String name) {
        userService.ensureAdmin();
        return userService.enableSeller(name);
    }

    @CachePut(value = "user", key = "#name")
    @PostMapping("/ReEnable-user/{name}")
    public String reEnableUser(@PathVariable String name) {
        userService.ensureAdmin();
        return userService.reEnableUser(name);
    }
    @PostMapping("/Disable-user/{name}")
    public String disableUser(@PathVariable String name) {
        userService.ensureAdmin();
        return userService.disableUser(name);
    }
    @PostMapping("/doLogout")
    public String logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
            if (authHeader != null) {
                String tokenValue = authHeader.replace("Bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);
            }
        userService.ensureUser();
        return "Logged out successfully";
    }

    @PostMapping("/Register/Seller")
    public String addSeller(@RequestBody Seller user,@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        return userService.addSeller(user,locale);
    }

    @PostMapping("/Register/Customer")
    public String addCustomer(@RequestBody Customer user,@RequestHeader(name = "Accept-Language", required = false) Locale locale){
        return userService.addCustomer(user,locale);
    }


    @GetMapping("/csv")
    public String convertCSV() {
        db2CsvExporterService.convert();
        return "Done";
    }






}
