package ayushproject.ayushecommerce;

import ayushproject.ayushecommerce.entities.*;
//import ayushproject.ayushecommerce.entities.ParentCategory.Electronics;
//import ayushproject.ayushecommerce.entities.ParentCategory.Fashion;
import ayushproject.ayushecommerce.enums.In_Stock;
import ayushproject.ayushecommerce.repo.*;
import ayushproject.ayushecommerce.security.PasswordValidator;
import ayushproject.ayushecommerce.services.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class Bootstrap implements ApplicationRunner {
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    UserService userService;
    @Autowired
    PasswordValidator passwordValidator;

    @Autowired
    CategoryFieldRepo categoryFieldRepo;
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    CategoryFeildValueRepo categoryFeildValueRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
       PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        Admin admin1 = new Admin();
        admin1.setFirstName("Ayush");
        admin1.setLastName("Thomas");
        admin1.setAge(24);
        admin1.setAdminid(301);
        admin1.setGender("M");
        admin1.setIs_active(true);
        admin1.setPassword(passwordEncoder.encode("Ayush@"));
        admin1.setName("ayushthomas09");
        admin1.setEmail("ayushthomas09@outlook.com");
        admin1.setAuthoritiesList(Arrays.asList("ROLE_ADMIN", "ROLE_CUSTOMER", "ROLE_SELLER"));
        admin1.setEnabled(true);
        Address address = new Address();
        address.setArea("Rohini");
        address.setHousenumber("Flat 103 D Block");
        address.setCity("New Delhi");
        address.setState("Delhi");
        address.setCountry("India");
        address.setPincode("110085");
        userRepo.save(admin1);

        Customer user1 = new Customer();
        user1.setFirstName("Kartik");
        user1.setLastName("Kumar");
        user1.setName("KartikKumar25");
        user1.setEmail("vinodthomas004@gmail.com");
        user1.setAuthoritiesList(Arrays.asList("ROLE_CUSTOMER"));
        File profilePic = new File("Static/image1.jpg");
        user1.setPassword(passwordEncoder.encode("Kartik@"));
        user1.setAddress(Arrays.asList(address));
        user1.setAge(21);
        user1.setGender("M");
        user1.setEnabled(true);
        user1.setIs_active(true);
        userRepo.save(user1);

        Seller seller1 = new Seller();
       seller1.setFirstName("Abhi");
        seller1.setLastName("Thomas");
        seller1.setPassword(passwordEncoder.encode("Abhi@"));
        seller1.setAuthoritiesList(Arrays.asList("ROLE_SELLER"));
        seller1.setAge(23);
        seller1.setIs_active(true);
        seller1.setEmail("ayushthomas09@gmail.com");
        seller1.setName("FashionMarket");
        seller1.setAddress(address);
        seller1.setGST_No(123456);
        seller1.setEnabled(true);
        seller1.setGender("M");
        userRepo.save(seller1);

        passwordValidator.setPassword(admin1.getPassword());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PasswordValidator>> constraintViolations = validator.validate(passwordValidator);

        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<PasswordValidator> violation : constraintViolations) {
                System.out.println(violation.getMessage());
            }
        } else {
            admin1.setPassword(passwordEncoder.encode(admin1.getPassword()));
            System.out.println("Valid Object");
            userRepo.save(admin1);
        }

        Category category1 = new Category();
        category1.setName("Electronics");
        categoryRepo.save(category1);

        Category subCategory = new Category();
        subCategory.setName("Phone");
        subCategory.setParentCategory(category1);
        categoryRepo.save(subCategory);

        CategoryField categoryFieldStorage = new CategoryField();
        categoryFieldStorage.setName("Storage");
        categoryFieldRepo.save(categoryFieldStorage);

        CategoryField categoryFieldStorage1 = new CategoryField();
        categoryFieldStorage1.setName("Colour");
        categoryFieldRepo.save(categoryFieldStorage1);

        CategoryFieldValues categoryFieldValues=new CategoryFieldValues();
        categoryFieldValues.setId(new CompositeKeyFieldValues(subCategory,categoryFieldStorage));
        categoryFieldValues.setPossibleValues(Arrays.asList("128 Gb","256 Gb"));
        categoryFeildValueRepo.save(categoryFieldValues);

        CategoryFieldValues categoryFieldValues1=new CategoryFieldValues();
        categoryFieldValues1.setId(new CompositeKeyFieldValues(subCategory,categoryFieldStorage1));
        categoryFieldValues1.setPossibleValues(Arrays.asList("Red","Black","Blue","Pink"));
        categoryFeildValueRepo.save(categoryFieldValues1);

        Category category2=new Category();
        category2.setName("Fashion");
        categoryRepo.save(category2);

        Category categoryShirt=new Category();
        categoryShirt.setName("Shirt");
        categoryShirt.setParentCategory(category2);
        categoryRepo.save(categoryShirt);

        CategoryField categoryFieldSize=new CategoryField();
        categoryFieldSize.setName("Size");
        categoryFieldRepo.save(categoryFieldSize);

        CategoryFieldValues categoryFieldValues2=new CategoryFieldValues();
        categoryFieldValues2.setId(new CompositeKeyFieldValues(categoryShirt,categoryFieldSize));
        categoryFieldValues2.setPossibleValues(Arrays.asList("S","M","L","XL","XXL"));
        categoryFeildValueRepo.save(categoryFieldValues2);

        CategoryField categoryFieldcolor = new CategoryField();
        categoryFieldcolor.setName("Colour");
        categoryFieldRepo.save(categoryFieldcolor);



        CategoryFieldValues categoryFieldValues3=new CategoryFieldValues();
        categoryFieldValues3.setId(new CompositeKeyFieldValues(categoryShirt,categoryFieldcolor));
        categoryFieldValues3.setPossibleValues(Arrays.asList("Red","Black","Blue","Pink"));
        categoryFeildValueRepo.save(categoryFieldValues3);


        Product phone = new Product();
        phone.setBrand("Apple");
        phone.setName("Iphone 11 Pro Max");
        phone.setDescription("Class Efficinet");
        phone.setInStock(In_Stock.Yes);
        phone.setQuantity(10);
        phone.setSellerId(3);
        phone.setPrice(100000);
        phone.setProductImage("resources/Static/image1.jpg");
        Map<String,String> metadata1=new HashMap<>();
        metadata1.put("Storage","128 GB");
        metadata1.put("Colour","Red");
        phone.setCategory(subCategory);
        phone.setMetaData(metadata1);
        productRepo.save(phone);

        Product shirt = new Product();
        shirt.setBrand("UCB");
        shirt.setQuantity(15);
        shirt.setSellerId(3);
        shirt.setInStock(In_Stock.Yes);
        shirt.setName("Shirt UCB");
        shirt.setActive(true);
        shirt.setPrice(5000);
        shirt.setProductImage("resources/Static/image1.jpg");
        shirt.setCategory(categoryShirt);
        productRepo.save(shirt);

        Product shirt1 = new Product();
        shirt1.setBrand("POLO");
        shirt1.setQuantity(15);
        shirt1.setSellerId(3);
        shirt1.setInStock(In_Stock.Yes);
        shirt1.setName("Shirt POLO");
        shirt1.setActive(true);
        shirt1.setPrice(2000);
        shirt1.setCategory(categoryShirt);
        productRepo.save(shirt1);

        shirt.setOtherVariationsId(Arrays.asList(shirt.getId()));
        shirt1.setOtherVariationsId(Arrays.asList(shirt1.getId()));
        productRepo.save(shirt);
        productRepo.save(shirt1);
        productRepo.save(phone);




    }
}