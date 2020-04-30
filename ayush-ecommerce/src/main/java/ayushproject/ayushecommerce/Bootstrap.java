package ayushproject.ayushecommerce;

import ayushproject.ayushecommerce.entities.*;
import ayushproject.ayushecommerce.entities.ParentCategory.Electronics;
import ayushproject.ayushecommerce.entities.ParentCategory.Fashion;
import ayushproject.ayushecommerce.enums.IN_STOCK;
import ayushproject.ayushecommerce.repo.*;
import ayushproject.ayushecommerce.security.PasswordValidatorClass;
import ayushproject.ayushecommerce.services.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
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
    PasswordValidatorClass passwordValidatorClass;

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
        user1.setEmail("kartik.kumar@tothenew.com");
        user1.setAuthoritiesList(Arrays.asList("ROLE_CUSTOMER"));
        user1.setPassword(passwordEncoder.encode("Kartik@"));
        user1.setAddress(Arrays.asList(address));
        user1.setAge(21);
        user1.setGender("M");
        user1.setEnabled(true);
        userRepo.save(user1);

        Seller seller1 = new Seller();
        seller1.setFirstName("Abhi");
        seller1.setLastName("Thomas");
        seller1.setPassword(passwordEncoder.encode("Abhi@"));
        seller1.setAuthoritiesList(Arrays.asList("ROLE_SELLER"));
        seller1.setAge(23);
        seller1.setEmail("ayushthomas09@gmail.com");
        seller1.setName("FashionMarket");
        seller1.setAddress(address);
        seller1.setGST_No(123456);
        seller1.setEnabled(true);
        seller1.setGender("M");
        userRepo.save(seller1);

        passwordValidatorClass.setPassword(admin1.getPassword());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PasswordValidatorClass>> constraintViolations = validator.validate(passwordValidatorClass);

        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<PasswordValidatorClass> violation : constraintViolations) {
                System.out.println(violation.getMessage());
            }
        } else {
            admin1.setPassword(passwordEncoder.encode(admin1.getPassword()));
            System.out.println("Valid Object");
            userRepo.save(admin1);
        }

        Electronics phone = new Electronics();
        phone.setBrand("Apple");
        phone.setProcessor("A13 Bionic");
        phone.setDisplay("5.1 Inch OLED");
        phone.setName("Iphone 11 Pro Max");
        phone.setDescription("Class Efficinet");
        phone.setInStock(IN_STOCK.Yes);
        phone.setQuantity(10);
        phone.setSellerId(3);
        phone.setStorage("256 gb");
        phone.setWarranty("1 Year");
        phone.setType("Cellular Phone");
        phone.setPrice(100000);
        phone.setProductImage("resources/Static/image1.jpg");
        productRepo.save(phone);

        Fashion shirt = new Fashion();
        shirt.setBrand("UCB");
        shirt.setColor("Red");
        shirt.setSize("L");
        shirt.setFit("Regular");
        shirt.setMaterial("Cotton 100%");
        shirt.setQuantity(15);
        shirt.setSellerId(3);
        shirt.setInStock(IN_STOCK.Yes);
        shirt.setName("Shirt UCB");
        shirt.setActive(true);
        shirt.setPrice(5000);
        shirt.setProductImage("resources/Static/image1.jpg");
        productRepo.save(shirt);

        Fashion shirt1 = new Fashion();
        shirt1.setBrand("POLO");
        shirt1.setColor("White");
        shirt1.setSize("L");
        shirt1.setFit("Regular");
        shirt1.setMaterial("Cotton 100%");
        shirt1.setQuantity(15);
        shirt1.setSellerId(3);
        shirt1.setInStock(IN_STOCK.Yes);
        shirt1.setName("Shirt POLO");
        shirt1.setActive(true);
        shirt1.setPrice(4000);
        productRepo.save(shirt1);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Storage", 128);
        phone.setMetadata(jsonObject);

        shirt.setOtherVariationsId(Arrays.asList(shirt.getId()));
        shirt1.setOtherVariationsId(Arrays.asList(shirt1.getId()));
        productRepo.save(shirt);
        productRepo.save(shirt1);
        productRepo.save(phone);

        Category category1 = new Category();
        category1.setName("Smart Phone");
        categoryRepo.save(category1);

        Category subCategory = new Category();
        subCategory.setName("Cellular");
        subCategory.setParentCategory(category1);
        categoryRepo.save(subCategory);

        CategoryField categoryFieldStorage = new CategoryField();
        categoryFieldStorage.setName("Storage");
        categoryFieldRepo.save(categoryFieldStorage);

        CategoryFieldValues categoryFieldValues=new CategoryFieldValues();
        categoryFieldValues.setId(new CompositeKeyFieldValues(category1,categoryFieldStorage));
        categoryFieldValues.setPossibleValues(Arrays.asList("128 Gb","256 Gb"));
        categoryFeildValueRepo.save(categoryFieldValues);

        Category category2=new Category();
        category2.setName("Fashion");
        categoryRepo.save(category2);

        Category categoryShirt=new Category();
        categoryShirt.setName("Shirt");
        categoryShirt.setParentId(10);
        categoryShirt.setParentCategory(category2);
        categoryRepo.save(categoryShirt);

        CategoryField categoryFieldSize=new CategoryField();
        categoryFieldSize.setName("Size");
        categoryFieldRepo.save(categoryFieldSize);

        CategoryFieldValues categoryFieldValues1=new CategoryFieldValues();
        categoryFieldValues1.setId(new CompositeKeyFieldValues(categoryShirt,categoryFieldSize));
        categoryFieldValues1.setPossibleValues(Arrays.asList("S","M","L","XL"));
        categoryFeildValueRepo.save(categoryFieldValues1);

    }
}