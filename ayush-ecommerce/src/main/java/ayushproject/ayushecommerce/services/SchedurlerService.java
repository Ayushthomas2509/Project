package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.repo.ProductRepository;
import ayushproject.ayushecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.Month;

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
    public void outOfStock(){
        List<Product> productList=productRepository.findAllByQuantity();
        System.out.println(productList);
        for(Product product: productList) {
            List<String> products=new ArrayList<>();
            products.add("Id, Product Name\n");
            products.add(product.getId() + ", " + product.getName() + "\n");
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userRepository.findById(product.getSellerId()).get().getEmail());
            mailMessage.setSubject("PRODUCT OUT OF STOCK");
            mailMessage.setFrom("ADMIN");
            mailMessage.setText("Hi Seller, \n This product is out of stock. \n"+products+"\n ");
            emailService.sendEmail(mailMessage);
        }

    }

    @Scheduled(cron = "0 40 01 * * ?")
    public void passwordExpired(){
//        User user = userRepository.findById(1).get();
        System.out.println("hii bro");
        List<User> userList= (List<User>) userRepository.findAll();
        for (User user: userList) {
            Date currentDate = new Date();
            if (user.getUpdatePasswordDate() != null) {
                Date updated = new Date(user.getUpdatePasswordDate().getYear(), user.getUpdatePasswordDate().getMonth(), user.getUpdatePasswordDate().getDate());
                if (updated.getDate() == currentDate.getDate() && updated.getMonth() == currentDate.getMonth() && updated.getYear() == currentDate.getYear()) {
                    System.out.println(updated);
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setTo(user.getEmail());
                    mailMessage.setSubject("ALERT! YOUR PASSWORD IS EXPIRED");
                    mailMessage.setFrom("ADMIN");
                    mailMessage.setText("Hi, \n As per terms your password has been expired. Click here to reset! http://localhost:8080/forgot-password/" + user.getName());
                    emailService.sendEmail(mailMessage);
                    user.setUpdatePasswordDate(updated);
                    user.setPassword(passwordEncoder.encode("bbll"));
                    userRepository.save(user);
                }
            }
        }

    }



}
