package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.repo.ProductRepository;
import ayushproject.ayushecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SchedurlerService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;
    @Autowired
    PasswordEncoder passwordEncoder;


    @Scheduled(cron = "0 42 22 * * ?")
    public void outOfStock() {
        List<Product> productList = productRepository.findAllByQuantity();
        System.out.println(productList);
        for (Product product : productList) {
            List<String> products = new ArrayList<>();
            products.add("Id, Product Name\n");
            products.add(product.getId() + ", " + product.getName() + "\n");
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userRepository.findById(product.getSellerId()).get().getEmail());
            mailMessage.setSubject("PRODUCT OUT OF STOCK");
            mailMessage.setFrom("ADMIN");
            mailMessage.setText("Hi Seller, \n This product is out of stock. \n" + products + "\n ");
            emailService.sendEmail(mailMessage);
        }

    }

    @Scheduled(cron = "0 0 12 */1 * ?")
    public void passwordExpired() {
        List<User> userList = (List<User>) userRepository.findAll();
        for (User user : userList) {
            LocalDate currentDate =  LocalDate.now();
            Integer i=6;
            if (user.getCreatedDate() != null) {
                if (user.getCreatedDate().plusMonths(6).equals(currentDate)){
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(user.getEmail());
                    mailMessage.setSubject("ALERT! YOUR PASSWORD IS EXPIRED");
                    mailMessage.setFrom("ADMIN");
                    mailMessage.setText("Hi, \n As per terms your password has been expired. Click here to reset! http://localhost:8080/forgot-password/" + user.getName());
                    emailService.sendEmail(mailMessage);
                    user.setCreatedDate(LocalDate.now());
                    user.setNonExpiredPassword(false);
                   // user.setIs_active(false);
//                    user.setPassword(passwordEncoder.encode("raw@1234"));
                    userRepository.save(user);

                }
            }
        }
    }
}