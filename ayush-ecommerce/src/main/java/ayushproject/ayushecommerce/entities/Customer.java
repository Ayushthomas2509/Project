package ayushproject.ayushecommerce.entities;

import ayushproject.ayushecommerce.security.GrantAuthorityImpl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
@Entity
@DiscriminatorValue("Customer")
public class Customer extends  User implements Serializable {
    private Integer custid;

    @ElementCollection
    private List<Address> address;
    @OneToMany
    private List<Reviews> reviews;
    @OneToMany
    private List<Product> products;

//    public Customer(String firstName) {
//        this.firstName= firstName;
//    }
//
//    public Customer(List<String> authoritiesList) {
//        setAuthoritiesList(Arrays.asList("ROLE_CUSTOMER"));
//        this.authoritiesList= authoritiesList;
//    }

    @OneToMany
    private List<Orders> orders;



    public Integer getCustid() {
        return custid;
    }

    public void setCustid(Integer custid) {
        this.custid = custid;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
