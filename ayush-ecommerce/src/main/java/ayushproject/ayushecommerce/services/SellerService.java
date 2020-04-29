package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Address;
import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.Seller;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.exceptions.WeakPasswordEx;
import ayushproject.ayushecommerce.repo.ProductRepo;
import ayushproject.ayushecommerce.repo.SellerRepo;
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
public class SellerService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    SellerRepo sellerRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    PasswordValidatorClass passwordValidatorClass;
    PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public Iterable<Seller> viewAll(){
        return sellerRepo.findAll();
    }

    public Seller findByName(String name){
        return sellerRepo.findByName(name);
    }

    public String editAddress(Address address,String name){
        Seller seller =sellerRepo.findByName(name);
        seller.setAddress(address);
        sellerRepo.save(seller);
        return "ADDRESS UPDATED";
    }

    public String updatePassword(String newPassword,String oldPassword,String name){
        User user=userRepo.findByname(name);
        if(passwordEncoder.matches(oldPassword,user.getPassword())) {
            Set<ConstraintViolation<PasswordValidatorClass>> constraintViolations = validate(passwordValidatorClass);
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

    private Set<ConstraintViolation<PasswordValidatorClass>> validate(PasswordValidatorClass passwordValidatorClass){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator=factory.getValidator();
        return validator.validate(passwordValidatorClass);
    }

    public List<Product> myProducts(String name){
        Seller seller=sellerRepo.findByName(name);
        return productRepo.perSellerProducts(seller.getId());
    }
}

