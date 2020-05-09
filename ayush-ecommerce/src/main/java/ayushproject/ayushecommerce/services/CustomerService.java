package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Address;
import ayushproject.ayushecommerce.entities.Customer;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.exceptions.ConfirmPasswordException;
import ayushproject.ayushecommerce.exceptions.WeakPasswordException;
import ayushproject.ayushecommerce.repo.CustomerRepository;
import ayushproject.ayushecommerce.repo.UserRepository;
import ayushproject.ayushecommerce.security.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class CustomerService {
   @Autowired
   CustomerRepository customerRepository;
   @Autowired
   UserRepository userRepository;
   @Autowired
   PasswordValidator passwordValidator;
   PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
   @Autowired
   ActivityLogService activityLogService;

    public User getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetail = (User) authentication.getPrincipal();
        String username = userDetail.getUsername();
        User user = userRepository.findByname(username);
        return user;
    }

   public Iterable<Customer> allCustomers()

   {        activityLogService.activityLog("Customer is Displayed","customer",null);
            return customerRepository.findAll();
   }

   public Customer myProfile(){
        User user=getLoggedInCustomer();
        activityLogService.activityLog("Customer Profile is Displayed","customer",null);

           return customerRepository.findByname(user.getName());
   }

   public List<Address> viewAdress(){
        activityLogService.activityLog("Customer Address is Displayed","customer",null);
           Customer customer = customerRepository.findByname(getLoggedInCustomer().getName());
       System.out.println("Address Is - ");
        return customer.getAddress();
   }

   public List<Address> addAddress(Address address){
       Customer customer= customerRepository.findByname(getLoggedInCustomer().getName());
       customer.getAddress().add(address);
       customerRepository.save(customer);
       return customer.getAddress();
   }

   public List<Address> editAddress(Address address,Integer addressId){
        activityLogService.activityLog("Address is added","customer",null);
           Customer customer= customerRepository.findByname(getLoggedInCustomer().getName());
        customer.getAddress().get(addressId).setPincode(address.getPincode());
       customer.getAddress().get(addressId).setCountry(address.getCountry());
       customer.getAddress().get(addressId).setCity(address.getCity());
       customer.getAddress().get(addressId).setState(address.getState());
       customer.getAddress().get(addressId).setHousenumber(address.getHousenumber());
       customer.getAddress().get(addressId).setArea(address.getArea());
       customerRepository.save(customer);
       return customer.getAddress();
   }

   public List<Address> deleteAddress(Integer addressId,String name){
        activityLogService.activityLog("Address is Deleted","customer",null);
        Customer customer= customerRepository.findByname(name);
        List<Address> addressList=customer.getAddress();
        addressList.remove(addressId);
        customer.setAddress(addressList);
        customerRepository.save(customer);
        return customer.getAddress();
   }

   public String updatePassword(String name, String newPassword, String oldPassword) {
        activityLogService.activityLog("Password is updated","customer",null);
        User user = userRepository.findByname(name);
       user.setCreatedDate( LocalDate.now());
       if (passwordEncoder.matches(oldPassword, user.getPassword())) {
           passwordValidator.setPassword(newPassword);
           Set<ConstraintViolation<PasswordValidator>> constraintViolations = violations(passwordValidator);
           if (constraintViolations.size() > 0) {
               throw new WeakPasswordException();
           } else {
               user.setPassword(passwordEncoder.encode(newPassword));
               userRepository.save(user);
               return "PASSWORD UPDATED";

           }
       }
           throw new ConfirmPasswordException("Password Dont Match");


   }


       private Set<ConstraintViolation<PasswordValidator>> violations(PasswordValidator passwordValidator){
           ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
           Validator validator = factory.getValidator();
           return validator.validate(passwordValidator);

       }



}

