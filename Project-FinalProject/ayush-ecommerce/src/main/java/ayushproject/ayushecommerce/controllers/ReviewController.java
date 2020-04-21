package ayushproject.ayushecommerce.controllers;

import ayushproject.ayushecommerce.entities.Reviews;
import ayushproject.ayushecommerce.services.ReviewService;
import ayushproject.ayushecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserService userService;

    @GetMapping("/reviews")
    public Iterable<Reviews> allreviews(){
        return reviewService.findall();
    }
    @GetMapping(value = "/products/{productId}/reviews")
    public Iterable<Reviews> findReviews(@PathVariable Integer productId)
    {
        return reviewService.findReviews(productId);
    }
    @PostMapping(value = "/addReview/products/{productId}")
    public String addToCart(@RequestBody Reviews review, @PathVariable Integer productId){
        return reviewService.addReview(review,productId);
    }
    @PostMapping(value = "/deleteReview/{reviewId}")
    public String deleteReview(@PathVariable Integer reviewId){
        return reviewService.deleteReview(reviewId);
    }
}
