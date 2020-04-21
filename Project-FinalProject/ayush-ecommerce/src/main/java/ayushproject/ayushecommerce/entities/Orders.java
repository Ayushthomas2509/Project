package ayushproject.ayushecommerce.entities;

import ayushproject.ayushecommerce.enums.STATUS;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderid;
    private STATUS  orderstatus;
    private Date orderdate;
    @Embedded
    private Address address;
    @ManyToMany
    private List<Product> products;
    @ElementCollection
    private List<Integer> quantity;
    @ElementCollection
    private List<Cart> carts;
    @ManyToOne
    private Customer user;

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public STATUS getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(STATUS orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }


    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
