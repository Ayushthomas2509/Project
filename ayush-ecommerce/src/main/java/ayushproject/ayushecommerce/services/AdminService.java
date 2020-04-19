package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.repo.CustomerRepo;
import ayushproject.ayushecommerce.repo.SellerRepo;
import ayushproject.ayushecommerce.repo.UserRepo;
import ayushproject.ayushecommerce.repo.VerificationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

@Component
public class AdminService {
@Autowired
    UserRepo userRepo;
@Autowired
    SellerRepo sellerRepo;
@Autowired
    CustomerRepo customerRepo;
@Autowired
    VerificationTokenRepo verificationTokenRepo;
@Autowired
    EmailService emailService;
@Autowired
    TokenStore tokenStore;
@Autowired
    ProductService productService;

}
