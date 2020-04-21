package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Customer;
import ayushproject.ayushecommerce.entities.Seller;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/users")
    public Iterable<User> allUsers(){
        userService.ensureAdmin();
        return userService.allUsers();
    }


    @GetMapping("users/{name}")
    public User findUser(@PathVariable String name)
    {     userService.ensureAdmin();
         return userService.findUser(name);
    }


    @GetMapping("users/edit")
    public String editUser(@RequestBody User user){
        userService.ensureUser();
        return userService.editUser(user);
    }

    @DeleteMapping("/users/remove/{name}")
    public String deleteUser(@PathVariable String name)
    {   userService.ensureAdmin();
        return userService.deleteUser(name);
    }

    @PostMapping("/forgot-password/{name}")
    public String forgotUserPassword(@PathVariable String name){
        userService.ensureUser();
        return userService.forgetPassword(name);
    }
    @GetMapping("/Password-Reset")
    public String validateResetToken(@RequestParam("token") String verificationToken,@RequestParam("newPassword") String newPassword) {
        return userService.validateResetToken(verificationToken,newPassword);
    }

    @GetMapping("/Account-Activation")
    public String validateActivationToken(@RequestParam("token") String verificationToken) {
        return userService.validateActivationToken(verificationToken);
    }

    @GetMapping("/Enable-seller/{name}")
    public String enableSeller(@PathVariable String name) {
        userService.ensureAdmin();
        return userService.enableSeller(name);
    }


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
    public String doLogout(HttpServletRequest request){
        return userService.logout(request);
    }

    @PostMapping("/users/addSeller")
    public String addSeller(@RequestBody Seller user){
        return userService.addSeller(user);
    }

    @PostMapping("/users/addCustomer")
    public String addCustomer(@RequestBody Customer user){
        return userService.addCustomer(user);
    }









}
