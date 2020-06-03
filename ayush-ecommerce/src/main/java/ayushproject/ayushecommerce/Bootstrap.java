package ayushproject.ayushecommerce;


import ayushproject.ayushecommerce.entities.*;
import ayushproject.ayushecommerce.enums.InStock;
import ayushproject.ayushecommerce.enums.Status;
import ayushproject.ayushecommerce.repo.*;
import ayushproject.ayushecommerce.security.PasswordValidator;
import ayushproject.ayushecommerce.services.Data;
import ayushproject.ayushecommerce.services.RandomUserCart;
import ayushproject.ayushecommerce.services.RandomUserData;
import ayushproject.ayushecommerce.services.UserService;
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
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Bootstrap implements ApplicationRunner {
 @Autowired
 UserRepository userRepository;
 @Autowired
 ProductRepository productRepository;
 @Autowired
 UserService userService;
 @Autowired
 PasswordValidator passwordValidator;

 @Autowired
 CategoryFieldRepositary categoryFieldRepositary;
 @Autowired
 CategoryRepository categoryRepository;
 @Autowired
 CategoryFeildValueRepository categoryFeildValueRepository;
 @Autowired
 ReviewRepository reviewRepository;
 @Autowired
 RandomUserData randomUserData;
 @Autowired
 CustomerRepository customerRepository;
 @Autowired
 RandomUserCart randomUserCart;
 @Autowired
 CartRepository cartRepository;
 @Autowired
 OrderRepository orderRepository;
 @Autowired
 OrderProductRepo orderProductRepo;


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
  //  admin1.setCreatedDate(new Date());
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
  userRepository.save(admin1);

  Customer user1 = new Customer();
  user1.setFirstName("Kartik");
  user1.setLastName("Kumar");
  user1.setName("KartikKumar25");
  user1.setEmail("ayush9005716605@gmail.com");
  user1.setCreatedDate(LocalDate.now());
  user1.setAuthoritiesList(Arrays.asList("ROLE_CUSTOMER"));
  File profilePic = new File("Static/image1.jpg");
  user1.setPassword(passwordEncoder.encode("Kartik@"));
  user1.setAddress(Arrays.asList(address));
  user1.setAge(21);
  user1.setGender("M");
  user1.setEnabled(true);
  user1.setIs_active(true);
  userRepository.save(user1);

  Seller seller1 = new Seller();
  seller1.setFirstName("Abhi");
  seller1.setLastName("Thomas");
  seller1.setCreatedDate(LocalDate.now());
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
  userRepository.save(seller1);

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
   userRepository.save(admin1);
  }

  Category category1 = new Category();
  category1.setName("Electronics");
  categoryRepository.save(category1);

  Category subCategory = new Category();
  subCategory.setName("Phone");
  subCategory.setParentCategory(category1);
  categoryRepository.save(subCategory);

  CategoryField categoryFieldStorage = new CategoryField();
  categoryFieldStorage.setName("Storage");
  categoryFieldRepositary.save(categoryFieldStorage);

  CategoryField categoryFieldStorage1 = new CategoryField();
  categoryFieldStorage1.setName("Colour");
  categoryFieldRepositary.save(categoryFieldStorage1);

  CategoryFieldValues categoryFieldValues = new CategoryFieldValues();
  categoryFieldValues.setId(new CompositeKeyFieldValues(subCategory, categoryFieldStorage));
  categoryFieldValues.setPossibleValues(Arrays.asList("128 Gb", "256 Gb"));
  categoryFeildValueRepository.save(categoryFieldValues);

  CategoryFieldValues categoryFieldValues1 = new CategoryFieldValues();
  categoryFieldValues1.setId(new CompositeKeyFieldValues(subCategory, categoryFieldStorage1));
  categoryFieldValues1.setPossibleValues(Arrays.asList("Red", "Black", "Blue", "Pink"));
  categoryFeildValueRepository.save(categoryFieldValues1);

  Category category2 = new Category();
  category2.setName("Fashion");
  categoryRepository.save(category2);

  Category categoryShirt = new Category();
  categoryShirt.setName("Shirt");
  categoryShirt.setParentCategory(category2);
  categoryRepository.save(categoryShirt);

  CategoryField categoryFieldSize = new CategoryField();
  categoryFieldSize.setName("Size");
  categoryFieldRepositary.save(categoryFieldSize);

  CategoryFieldValues categoryFieldValues2 = new CategoryFieldValues();
  categoryFieldValues2.setId(new CompositeKeyFieldValues(categoryShirt, categoryFieldSize));
  categoryFieldValues2.setPossibleValues(Arrays.asList("S", "M", "L", "XL", "XXL"));
  categoryFeildValueRepository.save(categoryFieldValues2);

  CategoryField categoryFieldcolor = new CategoryField();
  categoryFieldcolor.setName("Colour");
  categoryFieldRepositary.save(categoryFieldcolor);


  CategoryFieldValues categoryFieldValues3 = new CategoryFieldValues();
  categoryFieldValues3.setId(new CompositeKeyFieldValues(categoryShirt, categoryFieldcolor));
  categoryFieldValues3.setPossibleValues(Arrays.asList("Red", "Black", "Blue", "Pink"));
  categoryFeildValueRepository.save(categoryFieldValues3);


  Product phone = new Product();
  phone.setBrand("Apple");
  phone.setName("Iphone 11 Pro Max");
  phone.setDescription("Class Efficinet");
  phone.setInStock(InStock.Yes);
  phone.setQuantity(10);
  phone.setSellerId(3);
  phone.setPrice(100000);
  phone.setProductImage("resources/Static/image1.jpg");
  Map<String, String> metadata1 = new HashMap<>();
  metadata1.put("Storage", "128 GB");
  metadata1.put("Colour", "Red");
  phone.setCategory(subCategory);
  phone.setMetaData(metadata1);
  productRepository.save(phone);

  Product shirt = new Product();
  shirt.setBrand("UCB");
  shirt.setQuantity(15);
  shirt.setSellerId(3);
  shirt.setInStock(InStock.Yes);
  shirt.setName("Shirt UCB");
  shirt.setActive(true);
  shirt.setPrice(5000);
  shirt.setProductImage("resources/Static/image1.jpg");
  shirt.setCategory(categoryShirt);
  productRepository.save(shirt);

  Product shirt1 = new Product();
  shirt1.setBrand("POLO");
  shirt1.setQuantity(1);
  shirt1.setSellerId(3);
  shirt1.setInStock(InStock.Yes);
  shirt1.setName("Shirt POLO");
  shirt1.setActive(true);
  shirt1.setPrice(2000);
  shirt1.setCategory(categoryShirt);
  productRepository.save(shirt1);

//        shirt.setOtherVariationsId(Arrays.asList(shirt.getId()));
//        shirt1.setOtherVariationsId(Arrays.asList(shirt1.getId()));
  productRepository.save(shirt);
  productRepository.save(shirt1);
  productRepository.save(phone);

  Reviews reviews = new Reviews();
  reviews.setProductId(12);
  reviews.setReview("a bery Good Product");
  reviews.setStars(5);
  reviews.setUserId(user1.getId());
  reviewRepository.save(reviews);



  for (Integer i = 0; i < 100000; i++) {
   Customer customer = new Customer();
   customer.setAuthoritiesList(Arrays.asList("ROLE_CUSTOMER"));
   customer.setEmail(randomUserData.name() + "@gmail.com");
   customer.setName(randomUserData.name());
   customer.setFirstName(randomUserData.name());
   customer.setLastName(randomUserData.name());
   customer.setPassword(randomUserData.password());
   customer.setAge(randomUserData.number());
   customer.setDob(randomUserData.date());
   customerRepository.save(customer);
  }

  for (Integer j = 0; j < 50000; j++) {
   Address address1 = new Address();
   address1.setArea(randomUserCart.place());
   address1.setState(randomUserCart.place());
   address1.setCity(randomUserCart.place());
   address1.setCountry(randomUserCart.place());
   address1.setHousenumber(randomUserCart.houseno());
   address.setPincode(randomUserCart.zipcode());}

//         Cart cart = new Cart();
//         cart.setQuantity(randomUserCart.quantity());
//         cart.setUserid(randomUserCart.userId());
//         cart.setAddress(address1);
//         if(randomUserCart.quantity()/3==0) {
//          cart.setProductid(12);
//         }else if(randomUserCart.quantity()/5==0){
//          cart.setProductid(13);
//         }else{
//          cart.setProductid(14);
//         }
//         cartRepository.save(cart);
   // }
   for (Integer a = 0; a < 25000; a++) {
    Orders orders = new Orders();
    orders.setCustomerId(randomUserCart.userId());
    orders.setOrderStatus(Status.Placed);
    orders.setAmountPaid(randomUserCart.amount());
    orders.setDateCreate(randomUserData.date());
    Orders orders1 = orderRepository.save(orders);
    OrderProduct orderProduct = new OrderProduct();
    orderProduct.setOrderId(orders1.getOrderId());
    orderProduct.setDateCreated(randomUserData.date());
    orderProduct.setQuantity(randomUserCart.quantity());
    if (orderProduct.getQuantity() / 3 == 0) {
     orderProduct.setProductVariantId(12);
     orderProduct.setPrice(100000);
    } else if (orderProduct.getQuantity() / 5 == 0) {
     orderProduct.setProductVariantId(13);
     orderProduct.setPrice(5000);
    } else {
     orderProduct.setProductVariantId(14);
     orderProduct.setPrice(2000);
    }
    orderProductRepo.save(orderProduct);
    //System.out.println(a);
   }

//         Runnable ct1=new CustomerTask();
//         Runnable ct2=new CustomerTask();
//         Runnable ct3=new CustomerTask();
//         Runnable ct4=new CustomerTask();
//         Runnable ct5=new CustomerTask();
//         Runnable ct6=new CustomerTask();
//         Runnable ct7=new CustomerTask();
//         Runnable ct8=new CustomerTask();
//         Runnable ct9=new CustomerTask();
//         Runnable ct10=new CustomerTask();
//
//         ExecutorService executorService=Executors.newFixedThreadPool(10);
//         executorService.execute(ct1);
//         executorService.execute(ct2);
//         executorService.execute(ct3);
//         executorService.execute(ct4);
//         executorService.execute(ct5);
//         executorService.execute(ct6);
//         executorService.execute(ct7);
//         executorService.execute(ct8);
//         executorService.execute(ct9);
//         executorService.execute(ct10);
//
//         executorService.shutdown();


  }
 }
