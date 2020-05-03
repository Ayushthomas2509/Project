package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.Reviews;
import ayushproject.ayushecommerce.repo.CustomerRepository;
import ayushproject.ayushecommerce.repo.ProductRepository;
import ayushproject.ayushecommerce.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public  Iterable<Reviews> findall(){return reviewRepository.findAll();}

    public Iterable<Reviews> findReviews(Integer productId){
        return reviewRepository.findByProductId(productId);
    }
    public String addReview(Reviews reviews,Integer productId){
        Product product = productRepository.findById(productId).get();
        product.getReviews().add(reviews);
        reviews.setProductVariation(product.getName());
        reviews.setProductId(productId);
        customerRepository.findById(reviews.getUserId()).get().getReviews().add(reviews);
        reviewRepository.save(reviews);
        return "Review Added";
    }

    public String deleteReview(Integer reviewId){
        Reviews reviews= reviewRepository.findById(reviewId).get();
        Product product= productRepository.findById(reviewRepository.findById(reviewId).get().getProductId()).get();
        product.getReviews().remove(reviews);
        customerRepository.findById(reviewRepository.findById(reviewId).get().getUserId()).get().getReviews().remove(reviews);
        reviewRepository.delete(reviewRepository.findById(reviewId).get());
        return "Review Deleted";
    }
}
