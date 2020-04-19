package ayushproject.ayushecommerce.entities;

import ayushproject.ayushecommerce.enums.IN_STOCK;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.File;
import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Category",discriminatorType = DiscriminatorType.STRING)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Integer id;
    private String name;
    private String brand;
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    private IN_STOCK inStock;
    private Integer categoryId;
    private String description;
    private Boolean isDeleted=false;
    private Boolean isActive=false;
    private File productImage;
    private Integer price;
    private JSONObject metadata;

    @ElementCollection
    private List<Integer> otherVariationsId;

//    @Column(name = "created_date",nullable = false,updatable =false)
//    @CreatedDate
//    private Date createdDate;
//
//    @Column(name = "modified_date")
//    @LastModifiedDate
//    private Date modifiedDate;
//
//    @Column(name = "modified_by")
//    @LastModifiedBy
//    private String modifiedBy;

    @ManyToOne
    private Seller seller;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Reviews> reviews;

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public IN_STOCK getInStock() {
        return inStock;
    }

    public void setInStock(IN_STOCK inStock) {
        this.inStock = inStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public JSONObject getMetadata() {
        return metadata;
    }

    public void setMetadata(JSONObject metadata) {
        this.metadata = metadata;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<Integer> getOtherVariationsId() {
        return otherVariationsId;
    }

    public void setOtherVariationsId(List<Integer> otherVariationsId) {
        this.otherVariationsId = otherVariationsId;
    }

    public File getProductImage() {
        return productImage;
    }

    public void setProductImage(File productImage) {
        this.productImage = productImage;
    }

//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Date getModifiedDate() {
//        return modifiedDate;
//    }
//
//    public void setModifiedDate(Date modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }
//
//    public String getModifiedBy() {
//        return modifiedBy;
//    }
//
//    public void setModifiedBy(String modifiedBy) {
//        this.modifiedBy = modifiedBy;
//    }
}
