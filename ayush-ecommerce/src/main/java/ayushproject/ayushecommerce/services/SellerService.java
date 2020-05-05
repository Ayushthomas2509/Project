package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Address;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.Seller;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.exceptions.WeakPasswordException;
import ayushproject.ayushecommerce.repo.ProductRepository;
import ayushproject.ayushecommerce.repo.SellerRepository;
import ayushproject.ayushecommerce.repo.UserRepository;
import ayushproject.ayushecommerce.security.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SellerService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PasswordValidator passwordValidator;
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public Iterable<Seller> viewAll(){
        return sellerRepository.findAll();
    }

    public Seller findByName(String name){
        return sellerRepository.findByName(name);
    }

    public String editAddress(Address address,String name){
        Seller seller = sellerRepository.findByName(name);
        seller.setAddress(address);
        sellerRepository.save(seller);
        return "ADDRESS UPDATED";
    }

    public String updatePassword(String newPassword,String oldPassword,String name){
        User user= userRepository.findByname(name);
        Date date=new Date();
        user.setCreatedDate( LocalDate.now());
        if(passwordEncoder.matches(oldPassword,user.getPassword())) {
            Set<ConstraintViolation<PasswordValidator>> constraintViolations = validate(passwordValidator);
            if (constraintViolations.size() > 0) {
                throw new WeakPasswordException();
            } else {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                return "PASSWORD UPDATED";
            }
        }
            return "OLD PASSWORD DIDNT MATCH";

    }

    private Set<ConstraintViolation<PasswordValidator>> validate(PasswordValidator passwordValidator){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator=factory.getValidator();
        return validator.validate(passwordValidator);
    }

    public List<Product> myProducts(String name){
        Seller seller= sellerRepository.findByName(name);
        return productRepository.perSellerProducts(seller.getId());
    }
}

