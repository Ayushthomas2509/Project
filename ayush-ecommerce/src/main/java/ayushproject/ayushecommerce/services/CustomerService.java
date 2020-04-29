package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Address;
import ayushproject.ayushecommerce.entities.Customer;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.exceptions.WeakPasswordEx;
import ayushproject.ayushecommerce.repo.CustomerRepo;
import ayushproject.ayushecommerce.repo.UserRepo;
import ayushproject.ayushecommerce.security.PasswordValidatorClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Component
public class CustomerService {
   @Autowired
   CustomerRepo customerRepo;
   @Autowired
   UserRepo userRepo;
   @Autowired
   PasswordValidatorClass passwordValidatorClass;
   PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

   public Iterable<Customer> allCustomers(){
       return customerRepo.findAll();
   }

   public Customer findAll(String name){
       return customerRepo.findByname(name);
   }

   public List<Address> viewAdress(String name){
       Customer customer = customerRepo.findByname(name);
       System.out.println("Address Is - ");
        return customer.getAddress();
   }

   public List<Address> addAddress(Address address,String name){
       Customer customer=customerRepo.findByname(name);
       customer.getAddress().add(address);
       customerRepo.save(customer);
       return customer.getAddress();
   }

   public List<Address> editAddress(Address address,Integer addressId,String name){
       Customer customer=customerRepo.findByname(name);
       customer.getAddress().add(address);
       customerRepo.save(customer);
       return customer.getAddress();
   }

   public List<Address> deleteAddress(Integer addressId,String name){
       Customer customer=customerRepo.findByname(name);
       List<Address> addressList=customer.getAddress();
       addressList.remove(addressId);
       customer.setAddress(addressList);
       customerRepo.save(customer);
       return customer.getAddress();
   }

   public String updatePassword(String name, String newPassword, String oldPassword) {
       User user = userRepo.findByname(name);
       if (passwordEncoder.matches(oldPassword, user.getPassword())) {
           passwordValidatorClass.setPassword(newPassword);
           Set<ConstraintViolation<PasswordValidatorClass>> constraintViolations = violations(passwordValidatorClass);
           if (constraintViolations.size() > 0) {
               throw new WeakPasswordEx();
           } else {
               user.setPassword(passwordEncoder.encode(newPassword));
               userRepo.save(user);
               return "PASSWORD UPDATED";

           }
       }
           return "OLD PASSWORD DIDNT MATCH";


   }


       private Set<ConstraintViolation<PasswordValidatorClass>> violations(PasswordValidatorClass passwordValidatorClass){
           ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
           Validator validator = factory.getValidator();
           return validator.validate(passwordValidatorClass);

       }



}

