package ayushproject.ayushecommerce.entities;

import org.hibernate.envers.Audited;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//@Entity
@Document
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String reviewId;
    private Integer userId;
    @Audited
    private String username;
    private Integer productId;
    private String productVariation;
    private Integer stars;
    private String Review;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(String productVariation) {
        this.productVariation = productVariation;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
}


