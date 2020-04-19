package ayushproject.ayushecommerce.services;

import ayushproject.ayushecommerce.entities.Product;
import ayushproject.ayushecommerce.entities.Reviews;
import ayushproject.ayushecommerce.repo.CustomerRepo;
import ayushproject.ayushecommerce.repo.ProductRepo;
import ayushproject.ayushecommerce.repo.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewService {
    @Autowired
    UserService userService;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ReviewRepo reviewRepo;

    public  Iterable<Reviews> findall(){return reviewRepo.findAll();}

    public Iterable<Reviews> findReviews(Integer productId){
        return reviewRepo.findByProductId(productId);
    }
    public String addReview(Reviews reviews,Integer productId){
        Product product = productRepo.findById(productId).get();
        product.getReviews().add(reviews);
        reviews.setProductVariation(product.getName());
        reviews.setProductId(productId);
        customerRepo.findById(reviews.getUserId()).get().getReviews().add(reviews);
        reviewRepo.save(reviews);
        return "Review Added";
    }

    public String deleteReview(Integer reviewId){
        Reviews reviews=reviewRepo.findById(reviewId).get();
        Product product=productRepo.findById(reviewRepo.findById(reviewId).get().getProductId()).get();
        product.getReviews().remove(reviews);
        customerRepo.findById(reviewRepo.findById(reviewId).get().getUserId()).get().getReviews().remove(reviews);
        reviewRepo.delete(reviewRepo.findById(reviewId).get());
        return "Review Deleted";
    }
}
