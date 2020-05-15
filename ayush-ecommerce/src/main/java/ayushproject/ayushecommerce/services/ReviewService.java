package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.Reviews;
import ayushproject.ayushecommerce.entities.User;
import ayushproject.ayushecommerce.exceptions.UserNotFoundException;
import ayushproject.ayushecommerce.repo.CustomerRepository;
import ayushproject.ayushecommerce.repo.ProductRepository;
import ayushproject.ayushecommerce.repo.ReviewRepository;
import ayushproject.ayushecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ReviewService {
    @Autowired
    UserService userService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;

    public User getLoggedInCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetail = (User) authentication.getPrincipal();
        String username = userDetail.getUsername();
        User user = userRepository.findByname(username);
        return user;
    }

    public  Iterable<Reviews> findall(){return reviewRepository.findAll();}

    public Iterable<Reviews> findReviews(Integer productId){
        return reviewRepository.findByProductId(productId);
    }
    public String addReview(Reviews reviews,Integer productId){
        Product product = productRepository.findById(productId).get();
        reviews.setProductVariation(product.getName());
        reviews.setProductId(productId);
        reviews.setUserId(getLoggedInCustomer().getId());
        reviewRepository.save(reviews);
        return "Review Added";
    }

    public String deleteReview(Integer reviewId){
        Reviews reviews= reviewRepository.findById(reviewId).get();
        if(reviews.getReviewId() == null){
            throw new UserNotFoundException("Review Id is invalid");
        }
        if(reviews.getUserId()!= getLoggedInCustomer().getId()){
            throw new UserNotFoundException("This review Id is not associated with this user");
        }
        reviewRepository.delete(reviews);
        return "Review Deleted";
    }
}
